package ir.rayanovinmt.rnt_social_api.core.security.init.service.loader;

import ir.rayanovinmt.rnt_social_api.core.generator.model.PermissionModel;
import ir.rayanovinmt.rnt_social_api.core.generator.model.RoleModel;

import java.util.List;
import java.util.Map;

public interface SecurityDataLoader {
    Map<String, List<PermissionModel>> loadPermissions();
    Map<String, List<RoleModel>> loadRoles();
}
