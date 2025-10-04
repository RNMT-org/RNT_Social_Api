package ir.rayanovinmt.core.security.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

/**
 * DTO for frontend containing user's complete authorization context
 * This includes roles and permissions for frontend access control
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthorizationDto {
    Long userId;
    String username;
    String name;
    List<RoleAuthDto> roles;
    Set<String> allPermissions; // Flattened set of all permissions from all roles
}
