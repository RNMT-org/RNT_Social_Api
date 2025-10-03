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
        if (createDto.getMessage() != null && createDto.getMessage().getId() != null) {
            entity.setMessage(messageRepository.findById(createDto.getMessage().getId())
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + createDto.getMessage().getId())));
        }
        if (createDto.getKeyword() != null && createDto.getKeyword().getId() != null) {
            entity.setKeyword(keywordRepository.findById(createDto.getKeyword().getId())
                .orElseThrow(() -> new RuntimeException("Keyword not found with id: " + createDto.getKeyword().getId())));
        }
        if (createDto.getRecipient() != null && createDto.getRecipient().getId() != null) {
            entity.setRecipient(recipientRepository.findById(createDto.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + createDto.getRecipient().getId())));
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
        if (updateDto.getMessage() != null && updateDto.getMessage().getId() != null) {
            entity.setMessage(messageRepository.findById(updateDto.getMessage().getId())
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + updateDto.getMessage().getId())));
        }
        if (updateDto.getKeyword() != null && updateDto.getKeyword().getId() != null) {
            entity.setKeyword(keywordRepository.findById(updateDto.getKeyword().getId())
                .orElseThrow(() -> new RuntimeException("Keyword not found with id: " + updateDto.getKeyword().getId())));
        }
        if (updateDto.getRecipient() != null && updateDto.getRecipient().getId() != null) {
            entity.setRecipient(recipientRepository.findById(updateDto.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + updateDto.getRecipient().getId())));
        }

        AlertEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}