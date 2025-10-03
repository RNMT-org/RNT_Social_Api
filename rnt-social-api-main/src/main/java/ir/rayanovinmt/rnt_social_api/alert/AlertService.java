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

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlertService extends BaseService<AlertEntity , AlertCreateDto, AlertUpdateDto, AlertLoadDto> {
    AlertRepository repository;
    AlertMapper mapper = Mappers.getMapper(AlertMapper.class);

    @Override
    protected BaseRepository<AlertEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<AlertEntity , AlertCreateDto, AlertUpdateDto, AlertLoadDto> getMapper() {
        return mapper;
    }

}