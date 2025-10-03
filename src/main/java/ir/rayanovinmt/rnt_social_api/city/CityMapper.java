package ir.rayanovinmt.rnt_social_api.city;

import ir.rayanovinmt.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.city.dto.*;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileMapper;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileLoadDto;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileEntity;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface CityMapper extends BaseMapper<CityEntity, CityCreateDto, CityUpdateDto, CityLoadDto> {

    @Override
    @Mappings({
        @Mapping(target = "manager", ignore = true)
    })
    CityEntity create(CityCreateDto createDto);

    @Override
    @Mappings({
        @Mapping(target = "manager", ignore = true)
    })
    CityEntity entity(CityLoadDto loadDto);

    @Override
    @Mappings({
        @Mapping(target = "manager", ignore = true)
    })
    void update(CityUpdateDto updateDto, @MappingTarget CityEntity target);

}
