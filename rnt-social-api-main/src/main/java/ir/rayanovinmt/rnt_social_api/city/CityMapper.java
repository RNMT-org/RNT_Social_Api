package ir.rayanovinmt.rnt_social_api.city;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.city.dto.*;
import ir.rayanovinmt.core.security.user.UserMapper;
import ir.rayanovinmt.core.security.user.UserLoadDto;
import ir.rayanovinmt.core.security.user.User;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper extends BaseMapper<CityEntity, CityCreateDto, CityUpdateDto, CityLoadDto> {

    @Override
    CityLoadDto load(CityEntity entity);

    @Override
    @Mappings({
        @Mapping(target = "coreUser", ignore = true)
    })
    CityEntity create(CityCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "coreUser", ignore = true)
    })
    void update(CityUpdateDto updateDto, @MappingTarget CityEntity target);
}
