package ir.rayanovinmt.rnt_social_api.messagingplatform;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.*;

@Mapper(componentModel = "spring")
public interface MessagingPlatformMapper extends BaseMapper<MessagingPlatformEntity, MessagingPlatformCreateDto, MessagingPlatformUpdateDto, MessagingPlatformLoadDto> {

    @Override
    MessagingPlatformLoadDto load(MessagingPlatformEntity entity);

    @Override
    MessagingPlatformEntity create(MessagingPlatformCreateDto createDto);

    @Override
    void update(MessagingPlatformUpdateDto updateDto, @MappingTarget MessagingPlatformEntity target);
}
