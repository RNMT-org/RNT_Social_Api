package ir.rayanovinmt.core.security.permission;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.core.security.permission.dto.PermissionCreateDto;
import ir.rayanovinmt.core.security.permission.dto.PermissionLoadDto;
import ir.rayanovinmt.core.security.permission.dto.PermissionUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService extends BaseService<PermissionEntity, PermissionCreateDto, PermissionUpdateDto, PermissionLoadDto> {
    PermissionRepository repository;
    PermissionMapper mapper = Mappers.getMapper(PermissionMapper.class);

    @Override
    protected BaseRepository<PermissionEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<PermissionEntity, PermissionCreateDto, PermissionUpdateDto, PermissionLoadDto> getMapper() {
        return mapper;
    }

}
