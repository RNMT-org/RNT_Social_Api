package ir.rayanovinmt.core.security.permission;

import ir.rayanovinmt.core.entity.BaseMapper;
import ir.rayanovinmt.core.security.permission.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends BaseMapper<PermissionEntity, PermissionCreateDto, PermissionUpdateDto, PermissionLoadDto> {
}
