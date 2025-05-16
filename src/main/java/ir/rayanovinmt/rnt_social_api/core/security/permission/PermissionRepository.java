package ir.rayanovinmt.rnt_social_api.core.security.permission;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseRepository;
import ir.rayanovinmt.rnt_social_api.core.entity.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity> {
    Optional<PermissionEntity> findByName(String name);

    default PermissionEntity findByNameOrThrow(String name) {
        return findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Permission", "name", name));
    }
}