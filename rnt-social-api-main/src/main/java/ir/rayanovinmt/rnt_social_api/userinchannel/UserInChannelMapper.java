package ir.rayanovinmt.rnt_social_api.userinchannel;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.userinchannel.dto.*;
import ir.rayanovinmt.core.security.user.UserMapper;
import ir.rayanovinmt.core.security.user.UserLoadDto;
import ir.rayanovinmt.core.security.user.User;
import ir.rayanovinmt.rnt_social_api.channel.ChannelMapper;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelLoadDto;
import ir.rayanovinmt.rnt_social_api.channel.ChannelEntity;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserInChannelMapper extends BaseMapper<UserInChannelEntity, UserInChannelCreateDto, UserInChannelUpdateDto, UserInChannelLoadDto> {

    @Override
    UserInChannelLoadDto load(UserInChannelEntity entity);

    @Override
    @Mappings({
        @Mapping(target = "coreUser", ignore = true),
        @Mapping(target = "channel", ignore = true)
    })
    UserInChannelEntity create(UserInChannelCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "coreUser", ignore = true),
        @Mapping(target = "channel", ignore = true)
    })
    void update(UserInChannelUpdateDto updateDto, @MappingTarget UserInChannelEntity target);
}
