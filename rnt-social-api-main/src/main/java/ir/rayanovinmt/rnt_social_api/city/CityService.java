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
import jakarta.transaction.Transactional;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityService extends BaseService<CityEntity , CityCreateDto, CityUpdateDto, CityLoadDto> {
    CityRepository repository;
    CityMapper mapper = Mappers.getMapper(CityMapper.class);
    UserProfileRepository managerRepository;

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

    @Override
    @Transactional
    public CityLoadDto create(CityCreateDto createDto) {
        CityEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getManager() != null && createDto.getManager().getId() != null) {
            entity.setManager(managerRepository.findById(createDto.getManager().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + createDto.getManager().getId())));
        }

        CityEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public CityLoadDto update(Long id, CityUpdateDto updateDto) {
        CityEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getManager() != null && updateDto.getManager().getId() != null) {
            entity.setManager(managerRepository.findById(updateDto.getManager().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + updateDto.getManager().getId())));
        }

        CityEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}