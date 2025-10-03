package ir.rayanovinmt.rnt_social_api.tag;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.tag.dto.*;

@Mapper(componentModel = "spring", uses = {})
public interface TagMapper extends BaseMapper<TagEntity, TagCreateDto, TagUpdateDto, TagLoadDto> {
}
