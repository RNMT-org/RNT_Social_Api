package ir.rayanovinmt.rnt_social_api.core.security.role.dto;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleLoadDto extends BaseDto {
    String name;
}
