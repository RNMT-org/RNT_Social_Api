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
        if (createDto.getPlatform() != null && createDto.getPlatform().getId() != null) {
            entity.setPlatform(platformRepository.findById(createDto.getPlatform().getId())
                .orElseThrow(() -> new RuntimeException("MessagingPlatform not found with id: " + createDto.getPlatform().getId())));
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
        if (updateDto.getPlatform() != null && updateDto.getPlatform().getId() != null) {
            entity.setPlatform(platformRepository.findById(updateDto.getPlatform().getId())
                .orElseThrow(() -> new RuntimeException("MessagingPlatform not found with id: " + updateDto.getPlatform().getId())));
        }

        ChannelEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}