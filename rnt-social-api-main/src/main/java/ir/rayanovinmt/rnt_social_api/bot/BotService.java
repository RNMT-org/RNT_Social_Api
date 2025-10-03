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

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotService extends BaseService<BotEntity , BotCreateDto, BotUpdateDto, BotLoadDto> {
    BotRepository repository;
    BotMapper mapper = Mappers.getMapper(BotMapper.class);

    @Override
    protected BaseRepository<BotEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<BotEntity , BotCreateDto, BotUpdateDto, BotLoadDto> getMapper() {
        return mapper;
    }

}