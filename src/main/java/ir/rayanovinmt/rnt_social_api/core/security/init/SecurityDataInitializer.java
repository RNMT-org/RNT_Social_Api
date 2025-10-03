package ir.rayanovinmt.rnt_social_api.core.security.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.rayanovinmt.rnt_social_api.core.security.permission.PermissionEntity;
import ir.rayanovinmt.rnt_social_api.core.security.permission.PermissionRepository;
import ir.rayanovinmt.rnt_social_api.core.security.role.RoleEntity;
import ir.rayanovinmt.rnt_social_api.core.security.role.RoleRepository;
import ir.rayanovinmt.rnt_social_api.core.security.user.User;
import ir.rayanovinmt.rnt_social_api.core.security.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SecurityDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SecurityDataInitializer.class);

    private final SecurityInitConfig config;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) {
        if (!config.isEnabled()) {
            logger.info("Security initialization is disabled");
            return;
        }

        logger.info("Starting security data initialization...");

        try {
            // 1. Load and create all permissions
            Map<String, PermissionEntity> permissionMap = loadPermissions();
            logger.info("Loaded {} permissions", permissionMap.size());

            // 2. Load and create all roles with their permissions
            Map<String, RoleEntity> roleMap = loadRoles(permissionMap);
            logger.info("Loaded {} roles", roleMap.size());

            // 3. Create super admin user
            createSuperAdminUser(roleMap);

            logger.info("Security data initialization completed successfully!");

        } catch (Exception e) {
            logger.error("Failed to initialize security data", e);
            throw new RuntimeException("Security initialization failed", e);
        }
    }

    private Map<String, PermissionEntity> loadPermissions() throws IOException {
        Map<String, PermissionEntity> permissionMap = new HashMap<>();

        // Load permissions from all security folders
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:security/*/permissions.json");

        for (Resource resource : resources) {
            try {
                List<Map<String, String>> permissions = objectMapper.readValue(
                        resource.getInputStream(),
                        new TypeReference<List<Map<String, String>>>() {}
                );

                for (Map<String, String> permData : permissions) {
                    String name = permData.get("name");
                    String showName = permData.get("showName");

                    // Check if permission already exists
                    Optional<PermissionEntity> existing = permissionRepository.findByName(name);
                    PermissionEntity permission;

                    if (existing.isPresent()) {
                        permission = existing.get();
                        permission.setShowName(showName);
                        logger.debug("Updating existing permission: {}", name);
                    } else {
                        permission = PermissionEntity.builder()
                                .name(name)
                                .showName(showName)
                                .build();
                        logger.debug("Creating new permission: {}", name);
                    }

                    permission = permissionRepository.save(permission);
                    permissionMap.put(name, permission);
                }

                logger.info("Loaded permissions from: {}", resource.getFilename());
            } catch (Exception e) {
                logger.error("Error loading permissions from: " + resource.getFilename(), e);
            }
        }

        return permissionMap;
    }

    private Map<String, RoleEntity> loadRoles(Map<String, PermissionEntity> permissionMap) throws IOException {
        Map<String, RoleEntity> roleMap = new HashMap<>();

        // Load roles from all security folders
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:security/*/roles.json");

        for (Resource resource : resources) {
            try {
                List<Map<String, Object>> roles = objectMapper.readValue(
                        resource.getInputStream(),
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                for (Map<String, Object> roleData : roles) {
                    String roleName = (String) roleData.get("name");
                    @SuppressWarnings("unchecked")
                    List<String> permissionNames = (List<String>) roleData.get("permissions");

                    // Find all permissions for this role
                    Set<PermissionEntity> permissions = permissionNames.stream()
                            .map(permissionMap::get)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    // Check if role already exists
                    Optional<RoleEntity> existing = roleRepository.findByName(roleName);
                    RoleEntity role;

                    if (existing.isPresent()) {
                        role = existing.get();
                        role.setPermission(permissions);
                        logger.debug("Updating existing role: {}", roleName);
                    } else {
                        role = RoleEntity.builder()
                                .name(roleName)
                                .permission(permissions)
                                .build();
                        logger.debug("Creating new role: {}", roleName);
                    }

                    role = roleRepository.save(role);
                    roleMap.put(roleName, role);
                }

                logger.info("Loaded roles from: {}", resource.getFilename());
            } catch (Exception e) {
                logger.error("Error loading roles from: " + resource.getFilename(), e);
            }
        }

        return roleMap;
    }

    private void createSuperAdminUser(Map<String, RoleEntity> roleMap) {
        SecurityInitConfig.SuperAdminConfig adminConfig = config.getSuperAdmin();

        // Check if super admin already exists
        Optional<User> existingAdmin = userRepository.findByUsername(adminConfig.getUsername());

        if (existingAdmin.isPresent()) {
            logger.info("Super admin user '{}' already exists, updating roles and password", adminConfig.getUsername());
            User admin = existingAdmin.get();

            // Update password
            admin.setPassword(passwordEncoder.encode(adminConfig.getPassword()));

            // Update name
            admin.setName(adminConfig.getName());

            // Assign SUPER_ADMIN role and all other roles
            Set<RoleEntity> allRoles = new HashSet<>(roleMap.values());
            admin.setRoles(allRoles);

            userRepository.save(admin);
            logger.info("Super admin user '{}' updated with {} roles", adminConfig.getUsername(), allRoles.size());
        } else {
            logger.info("Creating super admin user: {}", adminConfig.getUsername());

            // Get all roles to assign to super admin
            Set<RoleEntity> allRoles = new HashSet<>(roleMap.values());

            User admin = User.builder()
                    .username(adminConfig.getUsername())
                    .password(passwordEncoder.encode(adminConfig.getPassword()))
                    .name(adminConfig.getName())
                    .roles(allRoles)
                    .build();

            userRepository.save(admin);
            logger.info("Super admin user '{}' created with {} roles", adminConfig.getUsername(), allRoles.size());
        }
    }
}
