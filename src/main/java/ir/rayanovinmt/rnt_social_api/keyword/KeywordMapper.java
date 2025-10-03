package ir.rayanovinmt.rnt_social_api.keyword;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.keyword.dto.*;
import ir.rayanovinmt.rnt_social_api.message.MessageMapper;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;
import ir.rayanovinmt.rnt_social_api.message.MessageEntity;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface KeywordMapper extends BaseMapper<KeywordEntity, KeywordCreateDto, KeywordUpdateDto, KeywordLoadDto> {

    @Override
    @Mappings({
        @Mapping(target = "messages", ignore = true)
    })
    KeywordEntity create(KeywordCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "messages", ignore = true)
    })
    KeywordEntity entity(KeywordLoadDto loadDto);

    @Override
    @Mappings({
        @Mapping(target = "messages", ignore = true)
    })
    void update(KeywordUpdateDto updateDto, @MappingTarget KeywordEntity target);

}
