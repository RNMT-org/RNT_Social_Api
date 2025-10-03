package ir.rayanovinmt.rnt_social_api.keyword;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordCreateDto;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordLoadDto;
import ir.rayanovinmt.rnt_social_api.keyword.dto.KeywordUpdateDto;
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
public class KeywordService extends BaseService<KeywordEntity , KeywordCreateDto, KeywordUpdateDto, KeywordLoadDto> {
    KeywordRepository repository;
    KeywordMapper mapper = Mappers.getMapper(KeywordMapper.class);

    @Override
    protected BaseRepository<KeywordEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<KeywordEntity , KeywordCreateDto, KeywordUpdateDto, KeywordLoadDto> getMapper() {
        return mapper;
    }

    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
            "text"
        );
    }
}