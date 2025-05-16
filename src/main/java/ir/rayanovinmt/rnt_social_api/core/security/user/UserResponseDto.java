package ir.rayanovinmt.rnt_social_api.core.security.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    String token;
    UserDto user;
}
