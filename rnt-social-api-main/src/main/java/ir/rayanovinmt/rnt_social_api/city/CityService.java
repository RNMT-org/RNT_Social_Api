package ir.rayanovinmt.rnt_social_api.city;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.city.dto.CityCreateDto;
import ir.rayanovinmt.rnt_social_api.city.dto.CityLoadDto;
import ir.rayanovinmt.rnt_social_api.city.dto.CityUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityService extends BaseService<CityEntity , CityCreateDto, CityUpdateDto, CityLoadDto> {
    CityRepository repository;
    CityMapper mapper = Mappers.getMapper(CityMapper.class);

    @Override
    protected BaseRepository<CityEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<CityEntity , CityCreateDto, CityUpdateDto, CityLoadDto> getMapper() {
        return mapper;
    }

    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
            "name"
        );
    }
}