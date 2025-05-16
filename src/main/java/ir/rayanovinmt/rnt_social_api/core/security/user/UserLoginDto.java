package ir.rayanovinmt.rnt_social_api.core.security.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDto {
    String username;
    String password;

}
