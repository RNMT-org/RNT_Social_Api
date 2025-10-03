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

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChannelService extends BaseService<ChannelEntity , ChannelCreateDto, ChannelUpdateDto, ChannelLoadDto> {
    ChannelRepository repository;
    ChannelMapper mapper = Mappers.getMapper(ChannelMapper.class);

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
}