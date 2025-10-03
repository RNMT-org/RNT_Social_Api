package ir.rayanovinmt.rnt_social_api.post;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import ir.rayanovinmt.rnt_social_api.core.entity.BaseRepository;
import ir.rayanovinmt.rnt_social_api.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.post.dto.PostCreateDto;
import ir.rayanovinmt.rnt_social_api.post.dto.PostLoadDto;
import ir.rayanovinmt.rnt_social_api.post.dto.PostUpdateDto;
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
public class PostService extends BaseService<PostEntity , PostCreateDto, PostUpdateDto, PostLoadDto> {
    PostRepository repository;
    PostMapper mapper = Mappers.getMapper(PostMapper.class);

    @Override
    protected BaseRepository<PostEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<PostEntity , PostCreateDto, PostUpdateDto, PostLoadDto> getMapper() {
        return mapper;
    }

    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
            "title", 
            "tag.name"
        );
    }
}