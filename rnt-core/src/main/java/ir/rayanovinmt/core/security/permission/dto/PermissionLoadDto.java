package ir.rayanovinmt.core.security.permission.dto;

import ir.rayanovinmt.core.entity.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionLoadDto extends BaseDto {
    String name;
    String showName;
}
