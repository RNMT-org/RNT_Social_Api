package ir.rayanovinmt.rnt_social_api.core.security.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * DTO for role with permissions for frontend authorization
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleAuthDto {
    Long roleId;
    String roleName;
    Set<PermissionAuthDto> permissions;
}
