package ir.rayanovinmt.core.security.user;

import ir.rayanovinmt.core.security.role.RoleEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String name;
    String username;
    Set<RoleEntity> roles;
    Date createdAt;
    Date updatedAt;

}
