package ir.rayanovinmt.rnt_social_api.message;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageCreateDto;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageLoadDto;
import ir.rayanovinmt.rnt_social_api.message.dto.MessageUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ir.rayanovinmt.rnt_social_api.channel.ChannelRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService extends BaseService<MessageEntity , MessageCreateDto, MessageUpdateDto, MessageLoadDto> {
    MessageRepository repository;
    MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
    ChannelRepository channelRepository;

    @Override
    protected BaseRepository<MessageEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<MessageEntity , MessageCreateDto, MessageUpdateDto, MessageLoadDto> getMapper() {
        return mapper;
    }

    @Override
    protected List<String> getSearchableFields() {
        return Arrays.asList(
            "text"
        );
    }

    @Override
    @Transactional
    public MessageLoadDto create(MessageCreateDto createDto) {
        MessageEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getChannel() != null && createDto.getChannel().getId() != null) {
            entity.setChannel(channelRepository.findById(createDto.getChannel().getId())
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + createDto.getChannel().getId())));
        }

        MessageEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public MessageLoadDto update(Long id, MessageUpdateDto updateDto) {
        MessageEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getChannel() != null && updateDto.getChannel().getId() != null) {
            entity.setChannel(channelRepository.findById(updateDto.getChannel().getId())
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + updateDto.getChannel().getId())));
        }

        MessageEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}