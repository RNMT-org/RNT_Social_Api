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

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService extends BaseService<MessageEntity , MessageCreateDto, MessageUpdateDto, MessageLoadDto> {
    MessageRepository repository;
    MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

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
}