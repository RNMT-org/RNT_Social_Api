package ir.rayanovinmt.rnt_social_api.messagingplatform;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformCreateDto;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformLoadDto;
import ir.rayanovinmt.rnt_social_api.messagingplatform.dto.MessagingPlatformUpdateDto;
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
public class MessagingPlatformService extends BaseService<MessagingPlatformEntity , MessagingPlatformCreateDto, MessagingPlatformUpdateDto, MessagingPlatformLoadDto> {
    MessagingPlatformRepository repository;
    MessagingPlatformMapper mapper = Mappers.getMapper(MessagingPlatformMapper.class);

    @Override
    protected BaseRepository<MessagingPlatformEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<MessagingPlatformEntity , MessagingPlatformCreateDto, MessagingPlatformUpdateDto, MessagingPlatformLoadDto> getMapper() {
        return mapper;
    }

    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
            "name"
        );
    }

}