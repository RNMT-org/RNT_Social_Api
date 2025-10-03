package ir.rayanovinmt.rnt_social_api.messagingplatform;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.*;

@Mapper(componentModel = "spring", uses = {})
public interface MessagingPlatformMapper extends BaseMapper<MessagingPlatformEntity, MessagingPlatformCreateDto, MessagingPlatformUpdateDto, MessagingPlatformLoadDto> {

    @Override
    @Mappings({
    })
    MessagingPlatformEntity create(MessagingPlatformCreateDto createDto);

    @Override
    @Mappings({
    })
    MessagingPlatformEntity entity(MessagingPlatformLoadDto loadDto);

    @Override
    @Mappings({
    })
    void update(MessagingPlatformUpdateDto updateDto, @MappingTarget MessagingPlatformEntity target);

}
