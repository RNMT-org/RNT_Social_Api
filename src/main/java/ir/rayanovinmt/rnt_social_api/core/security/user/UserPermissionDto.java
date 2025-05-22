package ir.rayanovinmt.rnt_social_api.core.security.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPermissionDto {
    String name;
    String showName;
}
