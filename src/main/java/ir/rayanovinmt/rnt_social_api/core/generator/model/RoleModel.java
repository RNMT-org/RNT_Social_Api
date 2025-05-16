package ir.rayanovinmt.rnt_social_api.core.generator.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleModel {
    String name;
    List<String> permissions;
}
