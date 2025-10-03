package ir.rayanovinmt.core.security.init.service.loader;

import ir.rayanovinmt.core.generator.model.PermissionModel;
import ir.rayanovinmt.core.generator.model.RoleModel;

import java.util.List;
import java.util.Map;

public interface SecurityDataLoader {
    Map<String, List<PermissionModel>> loadPermissions();
    Map<String, List<RoleModel>> loadRoles();
}
