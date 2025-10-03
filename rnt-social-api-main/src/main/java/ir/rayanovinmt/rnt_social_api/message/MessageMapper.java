package ir.rayanovinmt.rnt_social_api.message;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.message.dto.*;
import ir.rayanovinmt.rnt_social_api.channel.ChannelMapper;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelLoadDto;
import ir.rayanovinmt.rnt_social_api.channel.ChannelEntity;
import org.mapstruct.factory.Mappers;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordMapper;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordLoadDto;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordEntity;

@Mapper(componentModel = "spring", uses = {})
public interface MessageMapper extends BaseMapper<MessageEntity, MessageCreateDto, MessageUpdateDto, MessageLoadDto> {

    @Override
    @Mappings({
        @Mapping(target = "channel", ignore = true),
        @Mapping(target = "keywords", ignore = true)
    })
    MessageEntity create(MessageCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "channel", ignore = true),
        @Mapping(target = "keywords", ignore = true)
    })
    MessageEntity entity(MessageLoadDto loadDto);

    @Override
    @Mappings({
        @Mapping(target = "channel", ignore = true),
        @Mapping(target = "keywords", ignore = true)
    })
    void update(MessageUpdateDto updateDto, @MappingTarget MessageEntity target);


    default KeywordLoadDto keywordToKeywordDto(KeywordEntity keyword) {
        return Mappers.getMapper(KeywordMapper.class).load(keyword);
    }
}
