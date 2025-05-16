package ir.rayanovinmt.rnt_social_api.core.security.permission;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import ir.rayanovinmt.rnt_social_api.core.security.permission.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends BaseMapper<PermissionEntity, PermissionCreateDto, PermissionUpdateDto, PermissionLoadDto> {
}
