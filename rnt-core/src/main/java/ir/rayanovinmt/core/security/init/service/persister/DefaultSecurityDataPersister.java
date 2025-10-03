package ir.rayanovinmt.core.security.init.service.persister;

import ir.rayanovinmt.core.generator.model.PermissionModel;
import ir.rayanovinmt.core.generator.model.RoleModel;
import ir.rayanovinmt.core.security.permission.PermissionEntity;
import ir.rayanovinmt.core.security.permission.PermissionRepository;
import ir.rayanovinmt.core.security.role.RoleEntity;
import ir.rayanovinmt.core.security.role.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultSecurityDataPersister implements SecurityDataPersister {

    PermissionRepository permissionRepository;
    RoleRepository roleRepository;

    @Transactional
    public void persistPermissions(Map<String, List<PermissionModel>> permissionsMap) {
        for (var entry : permissionsMap.entrySet()) {
            for (PermissionModel model : entry.getValue()) {
                permissionRepository.findByName(model.getName())
                        .ifPresentOrElse(
                                existing -> {
                                    existing.setShowName(model.getShowName());
                                    permissionRepository.save(existing);
                                    log.info("Updated permission: name='{}', showName='{}'", model.getName(), model.getShowName());
                                },
                                () -> {
                                    PermissionEntity created = permissionRepository.save(
                                            PermissionEntity.builder()
                                                    .name(model.getName())
                                                    .showName(model.getShowName())
                                                    .build()
                                    );
                                    log.info("Created permission: name='{}', showName='{}'", created.getName(), created.getShowName());
                                }
                        );
            }
        }

        log.info("Permissions initialization complete.");
    }


    @Transactional
    public void persistRoles(Map<String, List<RoleModel>> rolesMap) {
        for (var entry : rolesMap.entrySet()) {
            for (RoleModel model : entry.getValue()) {
                AtomicBoolean isNew = new AtomicBoolean(false);
                RoleEntity role = roleRepository.findByName(model.getName())
                        .orElseGet(() -> {
                            isNew.set(true);
                            return RoleEntity.builder()
                                    .name(model.getName())
                                    .build();
                        });

                Set<PermissionEntity> permissions = model.getPermissions().stream()
                        .map(permissionRepository::findByNameOrThrow)
                        .collect(Collectors.toSet());

                role.setPermission(permissions);
                RoleEntity savedRole = roleRepository.save(role);

                if (isNew.get()) {
                    log.info("Created role: name='{}', permissions={}", savedRole.getName(),
                            permissions.stream().map(PermissionEntity::getName).collect(Collectors.toSet()));
                } else {
                    log.info("Updated role: name='{}', permissions={}", savedRole.getName(),
                            permissions.stream().map(PermissionEntity::getName).collect(Collectors.toSet()));
                }
            }
        }
        log.info("Roles initialization complete.");
    }
}

