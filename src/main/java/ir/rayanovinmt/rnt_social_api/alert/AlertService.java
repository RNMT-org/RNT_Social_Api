package ir.rayanovinmt.rnt_social_api.alert;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.alert.dto.AlertCreateDto;
import ir.rayanovinmt.rnt_social_api.alert.dto.AlertLoadDto;
import ir.rayanovinmt.rnt_social_api.alert.dto.AlertUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ir.rayanovinmt.rnt_social_api.message.MessageRepository;
import ir.rayanovinmt.rnt_social_api.keyword.KeywordRepository;
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlertService extends BaseService<AlertEntity , AlertCreateDto, AlertUpdateDto, AlertLoadDto> {
    AlertRepository repository;
    AlertMapper mapper = Mappers.getMapper(AlertMapper.class);
    MessageRepository messageRepository;
    KeywordRepository keywordRepository;
    UserProfileRepository recipientRepository;

    @Override
    protected BaseRepository<AlertEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<AlertEntity , AlertCreateDto, AlertUpdateDto, AlertLoadDto> getMapper() {
        return mapper;
    }


    @Override
    @Transactional
    public AlertLoadDto create(AlertCreateDto createDto) {
        AlertEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getMessageId() != null) {
            entity.setMessage(messageRepository.findById(createDto.getMessageId())
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + createDto.getMessageId())));
        }
        if (createDto.getKeywordId() != null) {
            entity.setKeyword(keywordRepository.findById(createDto.getKeywordId())
                .orElseThrow(() -> new RuntimeException("Keyword not found with id: " + createDto.getKeywordId())));
        }
        if (createDto.getRecipientId() != null) {
            entity.setRecipient(recipientRepository.findById(createDto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + createDto.getRecipientId())));
        }

        AlertEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public AlertLoadDto update(Long id, AlertUpdateDto updateDto) {
        AlertEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alert not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getMessageId() != null) {
            entity.setMessage(messageRepository.findById(updateDto.getMessageId())
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + updateDto.getMessageId())));
        }
        if (updateDto.getKeywordId() != null) {
            entity.setKeyword(keywordRepository.findById(updateDto.getKeywordId())
                .orElseThrow(() -> new RuntimeException("Keyword not found with id: " + updateDto.getKeywordId())));
        }
        if (updateDto.getRecipientId() != null) {
            entity.setRecipient(recipientRepository.findById(updateDto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + updateDto.getRecipientId())));
        }

        AlertEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}