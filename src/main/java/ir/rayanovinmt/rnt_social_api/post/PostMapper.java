package ir.rayanovinmt.rnt_social_api.post;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.post.dto.*;
import ir.rayanovinmt.rnt_social_api.person.PersonMapper;
import ir.rayanovinmt.rnt_social_api.person.dto.PersonLoadDto;
import ir.rayanovinmt.rnt_social_api.person.PersonEntity;
import org.mapstruct.factory.Mappers;
import ir.rayanovinmt.rnt_social_api.tag.TagMapper;
import ir.rayanovinmt.rnt_social_api.tag.dto.TagLoadDto;
import ir.rayanovinmt.rnt_social_api.tag.TagEntity;

@Mapper(componentModel = "spring")
public interface PostMapper extends BaseMapper<PostEntity, PostCreateDto, PostUpdateDto, PostLoadDto> {
}
