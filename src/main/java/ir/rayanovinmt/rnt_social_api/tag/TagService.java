package ir.rayanovinmt.rnt_social_api.tag;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import ir.rayanovinmt.rnt_social_api.core.entity.BaseRepository;
import ir.rayanovinmt.rnt_social_api.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.tag.dto.TagCreateDto;
import ir.rayanovinmt.rnt_social_api.tag.dto.TagLoadDto;
import ir.rayanovinmt.rnt_social_api.tag.dto.TagUpdateDto;
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
public class TagService extends BaseService<TagEntity , TagCreateDto, TagUpdateDto, TagLoadDto> {
    TagRepository repository;
    TagMapper mapper = Mappers.getMapper(TagMapper.class);

    @Override
    protected BaseRepository<TagEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<TagEntity , TagCreateDto, TagUpdateDto, TagLoadDto> getMapper() {
        return mapper;
    }

    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
            "name"
        );
    }
}