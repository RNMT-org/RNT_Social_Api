package ir.rayanovinmt.rnt_social_api.bot;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.bot.dto.*;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformMapper;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformLoadDto;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformEntity;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BotMapper extends BaseMapper<BotEntity, BotCreateDto, BotUpdateDto, BotLoadDto> {

    @Override
    BotLoadDto load(BotEntity entity);

    @Override
    @Mappings({
        @Mapping(target = "platform", ignore = true)
    })
    BotEntity create(BotCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "platform", ignore = true)
    })
    void update(BotUpdateDto updateDto, @MappingTarget BotEntity target);
}
