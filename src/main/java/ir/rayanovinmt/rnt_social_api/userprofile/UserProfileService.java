package ir.rayanovinmt.rnt_social_api.userprofile;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileCreateDto;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileLoadDto;
import ir.rayanovinmt.rnt_social_api.userprofile.dto.UserProfileUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ir.rayanovinmt.rnt_social_api.city.CityRepository;
import ir.rayanovinmt.core.security.user.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService extends BaseService<UserProfileEntity , UserProfileCreateDto, UserProfileUpdateDto, UserProfileLoadDto> {
    UserProfileRepository repository;
    UserProfileMapper mapper = Mappers.getMapper(UserProfileMapper.class);
    CityRepository affiliatedCityRepository;
    UserRepository userRepository;

    @Override
    protected BaseRepository<UserProfileEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<UserProfileEntity , UserProfileCreateDto, UserProfileUpdateDto, UserProfileLoadDto> getMapper() {
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
    public UserProfileLoadDto create(UserProfileCreateDto createDto) {
        UserProfileEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getAffiliatedCityId() != null) {
            entity.setAffiliatedCity(affiliatedCityRepository.findById(createDto.getAffiliatedCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + createDto.getAffiliatedCityId())));
        }
        if (createDto.getCoreUserId() != null) {
            entity.setCoreUser(userRepository.findById(createDto.getCoreUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + createDto.getCoreUserId())));
        }

        UserProfileEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public UserProfileLoadDto update(Long id, UserProfileUpdateDto updateDto) {
        UserProfileEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getAffiliatedCityId() != null) {
            entity.setAffiliatedCity(affiliatedCityRepository.findById(updateDto.getAffiliatedCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + updateDto.getAffiliatedCityId())));
        }
        if (updateDto.getCoreUserId() != null) {
            entity.setCoreUser(userRepository.findById(updateDto.getCoreUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + updateDto.getCoreUserId())));
        }

        UserProfileEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}