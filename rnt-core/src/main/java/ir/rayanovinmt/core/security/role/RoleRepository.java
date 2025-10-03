package ir.rayanovinmt.core.security.role;

import ir.rayanovinmt.core.entity.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity> {
    Optional<RoleEntity> findByName(String name);
}