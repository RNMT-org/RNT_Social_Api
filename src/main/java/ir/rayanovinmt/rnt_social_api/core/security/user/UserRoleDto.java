package ir.rayanovinmt.rnt_social_api.core.security.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleDto {
    String name;
    List<UserPermissionDto> permissions;
}
