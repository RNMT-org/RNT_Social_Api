package ir.rayanovinmt.rnt_social_api.person;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import ir.rayanovinmt.rnt_social_api.core.entity.BaseRepository;
import ir.rayanovinmt.rnt_social_api.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.person.dto.PersonCreateDto;
import ir.rayanovinmt.rnt_social_api.person.dto.PersonLoadDto;
import ir.rayanovinmt.rnt_social_api.person.dto.PersonUpdateDto;
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
public class PersonService extends BaseService<PersonEntity, PersonCreateDto, PersonUpdateDto, PersonLoadDto> {
    PersonRepository repository;
    PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Override
    protected BaseRepository<PersonEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<PersonEntity, PersonCreateDto, PersonUpdateDto, PersonLoadDto> getMapper() {
        return mapper;
    }

}
