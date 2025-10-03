package ir.rayanovinmt.rnt_social_api.bot;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.bot.dto.BotCreateDto;
import ir.rayanovinmt.rnt_social_api.bot.dto.BotLoadDto;
import ir.rayanovinmt.rnt_social_api.bot.dto.BotUpdateDto;
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
public class BotService extends BaseService<BotEntity , BotCreateDto, BotUpdateDto, BotLoadDto> {
    BotRepository repository;
    BotMapper mapper = Mappers.getMapper(BotMapper.class);
    MessagingPlatformRepository platformRepository;

    @Override
    protected BaseRepository<BotEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<BotEntity , BotCreateDto, BotUpdateDto, BotLoadDto> getMapper() {
        return mapper;
    }


    @Override
    @Transactional
    public BotLoadDto create(BotCreateDto createDto) {
        BotEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getPlatform() != null && createDto.getPlatform().getId() != null) {
            entity.setPlatform(platformRepository.findById(createDto.getPlatform().getId())
                .orElseThrow(() -> new RuntimeException("MessagingPlatform not found with id: " + createDto.getPlatform().getId())));
        }

        BotEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public BotLoadDto update(Long id, BotUpdateDto updateDto) {
        BotEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Bot not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getPlatform() != null && updateDto.getPlatform().getId() != null) {
            entity.setPlatform(platformRepository.findById(updateDto.getPlatform().getId())
                .orElseThrow(() -> new RuntimeException("MessagingPlatform not found with id: " + updateDto.getPlatform().getId())));
        }

        BotEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}