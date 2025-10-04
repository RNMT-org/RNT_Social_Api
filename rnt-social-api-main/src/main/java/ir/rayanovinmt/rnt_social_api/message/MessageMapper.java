package ir.rayanovinmt.rnt_social_api.message;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.message.dto.*;
import ir.rayanovinmt.rnt_social_api.channel.ChannelMapper;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelLoadDto;
import ir.rayanovinmt.rnt_social_api.channel.ChannelEntity;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper extends BaseMapper<MessageEntity, MessageCreateDto, MessageUpdateDto, MessageLoadDto> {

    @Override
    MessageLoadDto load(MessageEntity entity);

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
    void update(MessageUpdateDto updateDto, @MappingTarget MessageEntity target);
}
