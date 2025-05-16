package ir.rayanovinmt.rnt_social_api.core.security.init.service.persister;

import ir.rayanovinmt.rnt_social_api.core.generator.model.PermissionModel;
import ir.rayanovinmt.rnt_social_api.core.generator.model.RoleModel;

import java.util.List;
import java.util.Map;

public interface SecurityDataPersister {
    void persistPermissions(Map<String, List<PermissionModel>> permissions);
    void persistRoles(Map<String, List<RoleModel>> roles);
}
