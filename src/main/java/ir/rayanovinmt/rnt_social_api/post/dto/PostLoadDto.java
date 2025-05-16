package ir.rayanovinmt.rnt_social_api.post.dto;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ir.rayanovinmt.rnt_social_api.person.dto.PersonLoadDto;
import ir.rayanovinmt.rnt_social_api.tag.dto.TagLoadDto;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostLoadDto extends BaseDto {
    String title;
}
