package ir.rayanovinmt.core.security.role;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.security.role.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleEntity, RoleCreateDto, RoleUpdateDto, RoleLoadDto> {
}
