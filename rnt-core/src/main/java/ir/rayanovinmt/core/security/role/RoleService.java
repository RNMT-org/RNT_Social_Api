package ir.rayanovinmt.core.security.role;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.entity.BaseRepository;
import ir.rayanovinmt.core.entity.BaseService;
import ir.rayanovinmt.core.security.role.dto.RoleCreateDto;
import ir.rayanovinmt.core.security.role.dto.RoleLoadDto;
import ir.rayanovinmt.core.security.role.dto.RoleUpdateDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService extends BaseService<RoleEntity, RoleCreateDto, RoleUpdateDto, RoleLoadDto> {
    RoleRepository repository;
    RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

    @Override
    protected BaseRepository<RoleEntity> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<RoleEntity, RoleCreateDto, RoleUpdateDto, RoleLoadDto> getMapper() {
        return mapper;
    }

}
