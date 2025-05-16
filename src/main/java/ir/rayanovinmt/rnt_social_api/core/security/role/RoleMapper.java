package ir.rayanovinmt.rnt_social_api.core.security.role;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import ir.rayanovinmt.rnt_social_api.core.security.role.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleEntity, RoleCreateDto, RoleUpdateDto, RoleLoadDto> {
}
