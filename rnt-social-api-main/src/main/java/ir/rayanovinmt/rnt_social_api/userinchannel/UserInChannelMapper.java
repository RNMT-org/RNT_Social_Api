package ir.rayanovinmt.rnt_social_api.userinchannel;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.userinchannel.dto.*;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileMapper;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileLoadDto;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileEntity;
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
        @Mapping(target = "user", ignore = true),
        @Mapping(target = "channel", ignore = true)
    })
    UserInChannelEntity create(UserInChannelCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "user", ignore = true),
        @Mapping(target = "channel", ignore = true)
    })
    void update(UserInChannelUpdateDto updateDto, @MappingTarget UserInChannelEntity target);
}
