package ir.rayanovinmt.rnt_social_api.channel;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelCreateDto;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelLoadDto;
import ir.rayanovinmt.rnt_social_api.channel.dto.ChannelUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ir.rayanovinmt.rnt_social_api.messagingplatform.MessagingPlatformRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChannelService extends BaseService<ChannelEntity , ChannelCreateDto, ChannelUpdateDto, ChannelLoadDto> {
    ChannelRepository repository;
    ChannelMapper mapper = Mappers.getMapper(ChannelMapper.class);
    MessagingPlatformRepository platformRepository;

    @Override
    protected BaseRepository<ChannelEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<ChannelEntity , ChannelCreateDto, ChannelUpdateDto, ChannelLoadDto> getMapper() {
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
    public ChannelLoadDto create(ChannelCreateDto createDto) {
        ChannelEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getPlatformId() != null) {
            entity.setPlatform(platformRepository.findById(createDto.getPlatformId())
                .orElseThrow(() -> new RuntimeException("MessagingPlatform not found with id: " + createDto.getPlatformId())));
        }

        ChannelEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public ChannelLoadDto update(Long id, ChannelUpdateDto updateDto) {
        ChannelEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Channel not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getPlatformId() != null) {
            entity.setPlatform(platformRepository.findById(updateDto.getPlatformId())
                .orElseThrow(() -> new RuntimeException("MessagingPlatform not found with id: " + updateDto.getPlatformId())));
        }

        ChannelEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}