package ir.rayanovinmt.rnt_social_api.userprofile;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.*;
import ir.rayanovinmt.rnt_social_api.city.CityMapper;
import ir.rayanovinmt.rnt_social_api.city.dto.CityLoadDto;
import ir.rayanovinmt.rnt_social_api.city.CityEntity;
import ir.rayanovinmt.core.security.user.UserMapper;
import ir.rayanovinmt.core.security.user.UserLoadDto;
import ir.rayanovinmt.core.security.user.User;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseMapper<UserProfileEntity, UserProfileCreateDto, UserProfileUpdateDto, UserProfileLoadDto> {

    @Override
    UserProfileLoadDto load(UserProfileEntity entity);

    @Override
    @Mappings({
        @Mapping(target = "affiliatedCity", ignore = true),
        @Mapping(target = "coreUser", ignore = true)
    })
    UserProfileEntity create(UserProfileCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "affiliatedCity", ignore = true),
        @Mapping(target = "coreUser", ignore = true)
    })
    void update(UserProfileUpdateDto updateDto, @MappingTarget UserProfileEntity target);
}
