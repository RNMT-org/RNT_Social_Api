package ir.rayanovinmt.rnt_social_api.core.security.role;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseEntity;
import ir.rayanovinmt.rnt_social_api.core.security.permission.PermissionEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(nullable = false)
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permission",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<PermissionEntity> permission;
}