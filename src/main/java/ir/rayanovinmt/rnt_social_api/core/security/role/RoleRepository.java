package ir.rayanovinmt.rnt_social_api.core.security.role;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity> {
    Optional<RoleEntity> findByName(String name);
}