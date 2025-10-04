package ir.rayanovinmt.core.security.constant;

/**
 * System-level permission constants for user, role, and permission management
 */
public class SystemPermission {
    // User permissions
    public static final String USER_READ = "user:read";
    public static final String USER_ASSIGN_ROLES = "user:assign_roles";

    // Role permissions
    public static final String ROLE_ROOT = "role:root";
    public static final String ROLE_CREATE = "role:create";
    public static final String ROLE_UPDATE = "role:update";
    public static final String ROLE_READ = "role:read";
    public static final String ROLE_SEARCH = "role:search";
    public static final String ROLE_DELETE = "role:delete";

    // Permission permissions
    public static final String PERMISSION_ROOT = "permission:root";
    public static final String PERMISSION_CREATE = "permission:create";
    public static final String PERMISSION_UPDATE = "permission:update";
    public static final String PERMISSION_READ = "permission:read";
    public static final String PERMISSION_SEARCH = "permission:search";
    public static final String PERMISSION_DELETE = "permission:delete";

    private SystemPermission() {
        // Utility class
    }
}
