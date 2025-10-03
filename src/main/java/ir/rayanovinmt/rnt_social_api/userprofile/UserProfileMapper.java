package ir.rayanovinmt.rnt_social_api.userprofile;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.*;
import ir.rayanovinmt.rnt_social_api.city.CityMapper;
import ir.rayanovinmt.rnt_social_api.city.dto.CityLoadDto;
import ir.rayanovinmt.rnt_social_api.city.CityEntity;
import org.mapstruct.factory.Mappers;
import ir.rayanovinmt.core.security.user.UserMapper;
import ir.rayanovinmt.core.security.user.UserDto;
import ir.rayanovinmt.core.security.user.User;

@Mapper(componentModel = "spring", uses = {})
public interface UserProfileMapper extends BaseMapper<UserProfileEntity, UserProfileCreateDto, UserProfileUpdateDto, UserProfileLoadDto> {

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
    UserProfileEntity entity(UserProfileLoadDto loadDto);

    @Override
    @Mappings({
        @Mapping(target = "affiliatedCity", ignore = true),
        @Mapping(target = "coreUser", ignore = true)
    })
    void update(UserProfileUpdateDto updateDto, @MappingTarget UserProfileEntity target);

}
