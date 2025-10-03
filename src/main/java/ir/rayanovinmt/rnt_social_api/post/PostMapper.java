package ir.rayanovinmt.rnt_social_api.post;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.post.dto.*;

@Mapper(componentModel = "spring", uses = {})
public interface PostMapper extends BaseMapper<PostEntity, PostCreateDto, PostUpdateDto, PostLoadDto> {
}
