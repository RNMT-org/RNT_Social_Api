package ir.rayanovinmt.rnt_social_api.core.security.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleAssignDto {
    @NotNull(message = "User ID is required")
    Long userId;

    @NotEmpty(message = "At least one role ID is required")
    Set<Long> roleIds;
}
