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
import ir.rayanovinmt.rnt_social_api.userprofile.UserProfileRepository;
import ir.rayanovinmt.rnt_social_api.channel.ChannelRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInChannelService extends BaseService<UserInChannelEntity , UserInChannelCreateDto, UserInChannelUpdateDto, UserInChannelLoadDto> {
    UserInChannelRepository repository;
    UserInChannelMapper mapper = Mappers.getMapper(UserInChannelMapper.class);
    UserProfileRepository userRepository;
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
        if (createDto.getUserId() != null) {
            entity.setUser(userRepository.findById(createDto.getUserId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + createDto.getUserId())));
        }
        if (createDto.getChannelId() != null) {
            entity.setChannel(channelRepository.findById(createDto.getChannelId())
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + createDto.getChannelId())));
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
        if (updateDto.getUserId() != null) {
            entity.setUser(userRepository.findById(updateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + updateDto.getUserId())));
        }
        if (updateDto.getChannelId() != null) {
            entity.setChannel(channelRepository.findById(updateDto.getChannelId())
                .orElseThrow(() -> new RuntimeException("Channel not found with id: " + updateDto.getChannelId())));
        }

        UserInChannelEntity updatedEntity = repository.save(entity);
        return mapper.load(updatedEntity);
    }
}