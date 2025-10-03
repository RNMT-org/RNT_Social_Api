package ir.rayanovinmt.rnt_social_api.channel;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.channel.dto.*;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformMapper;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformLoadDto;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformEntity;
import org.mapstruct.factory.Mappers;
import ir.rayanovinmt.rnt_social_api.city.CityMapper;
import ir.rayanovinmt.rnt_social_api.city.dto.CityLoadDto;
import ir.rayanovinmt.rnt_social_api.city.CityEntity;
import ir.rayanovinmt.rnt_social_api.bot.BotMapper;
import ir.rayanovinmt.rnt_social_api.bot.dto.BotLoadDto;
import ir.rayanovinmt.rnt_social_api.bot.BotEntity;

@Mapper(componentModel = "spring", uses = {})
public interface ChannelMapper extends BaseMapper<ChannelEntity, ChannelCreateDto, ChannelUpdateDto, ChannelLoadDto> {

    @Override
    @Mappings({
        @Mapping(target = "platform", ignore = true),
        @Mapping(target = "cities", ignore = true),
        @Mapping(target = "bots", ignore = true)
    })
    ChannelEntity create(ChannelCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "platform", ignore = true),
        @Mapping(target = "cities", ignore = true),
        @Mapping(target = "bots", ignore = true)
    })
    ChannelEntity entity(ChannelLoadDto loadDto);

    @Override
    @Mappings({
        @Mapping(target = "platform", ignore = true),
        @Mapping(target = "cities", ignore = true),
        @Mapping(target = "bots", ignore = true)
    })
    void update(ChannelUpdateDto updateDto, @MappingTarget ChannelEntity target);

}
