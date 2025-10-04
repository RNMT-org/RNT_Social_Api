package ir.rayanovinmt.rnt_social_api.userinchannel;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.rnt_social_api.userinchannel.dto.UserInChannelCreateDto;
import ir.rayanovinmt.rnt_social_api.userinchannel.dto.UserInChannelLoadDto;
import ir.rayanovinmt.rnt_social_api.userinchannel.dto.UserInChannelUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ir.rayanovinmt.core.security.user.UserRepository;
import ir.rayanovinmt.rnt_social_api.channel.ChannelRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInChannelService extends BaseService<UserInChannelEntity , UserInChannelCreateDto, UserInChannelUpdateDto, UserInChannelLoadDto> {
    UserInChannelRepository repository;
    UserInChannelMapper mapper = Mappers.getMapper(UserInChannelMapper.class);
    UserRepository userRepository;
    ChannelRepository channelRepository;

    @Override
    protected BaseRepository<UserInChannelEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<UserInChannelEntity , UserInChannelCreateDto, UserInChannelUpdateDto, UserInChannelLoadDto> getMapper() {
        return mapper;
    }


    @Override
    @Transactional
    public UserInChannelLoadDto create(UserInChannelCreateDto createDto) {
        UserInChannelEntity entity = mapper.create(createDto);

        // Set relationships
        if (createDto.getCoreUser() != null && createDto.getCoreUser().getId() != null) {
            entity.setCoreUser(userRepository.findById(createDto.getCoreUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + createDto.getCoreUser().getId())));
        }
        if (createDto.getChannel() != null && createDto.getChannel().getId() != null) {
            entity.setChannel(channelRepository.findById(createDto.getChannel().getId())
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + createDto.getChannel().getId())));
        }

        UserInChannelEntity savedEntity = repository.save(entity);
        return mapper.load(savedEntity);
    }

    @Override
    @Transactional
    public UserInChannelLoadDto update(Long id, UserInChannelUpdateDto updateDto) {
        UserInChannelEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserInChannel not found with id: " + id));

        mapper.update(updateDto, entity);

        // Update relationships
        if (updateDto.getCoreUser() != null && updateDto.getCoreUser().getId() != null) {
            entity.setCoreUser(userRepository.findById(updateDto.getCoreUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + updateDto.getCoreUser().getId())));
        }
        if (updateDto.getChannel() != null && updateDto.getChannel().getId() != null) {
            entity.setChannel(channelRepository.findById(updateDto.getChannel().getId())
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + updateDto.getChannel().getId())));
        }

        UserInChannelEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}