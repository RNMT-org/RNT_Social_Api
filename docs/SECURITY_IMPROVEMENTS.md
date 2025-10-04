# Security System Improvements

## Overview
Comprehensive security improvements to the RNT Social API, addressing critical authorization vulnerabilities and adding production-ready user, role, and permission management.

---

## 🔒 Critical Security Fixes

### 1. **User Update Endpoint** ✅
**Problem**: Users had no secure way to update their profile information.

**Solution**: Added secure user update endpoint at `PUT /api/auth`
- Users can update their own name
- Users can securely change their password (auto-encrypted)
- Validates current user from JWT token
- Full validation with Jakarta Bean Validation

**Files Created**:
- `UserUpdateDto.java` - DTO for user updates

**Files Modified**:
- `UserService.java` - Added `update()` method
- `UserController.java` - Added `@PutMapping` endpoint

---

### 2. **Role & Permission Management Authorization** ✅
**Problem**: Role and Permission endpoints had NO authorization - anyone could create/update/delete roles and permissions!

**Solution**: Added `@HasPermission` annotations to all role/permission endpoints:

**Role Controller** (`/api/role`):
- `POST /` → `@HasPermission("role:create")`
- `PUT /{id}` → `@HasPermission("role:update")`
- `GET /{id}` → `@HasPermission("role:read")`
- `GET /` → `@HasPermission("role:search")`
- `DELETE /{id}` → `@HasPermission("role:delete")`

**Permission Controller** (`/api/permission`):
- `POST /` → `@HasPermission("permission:create")`
- `PUT /{id}` → `@HasPermission("permission:update")`
- `GET /{id}` → `@HasPermission("permission:read")`
- `GET /` → `@HasPermission("permission:search")`
- `DELETE /{id}` → `@HasPermission("permission:delete")`

**Files Modified**:
- `RoleController.java`
- `PermissionController.java`

---

### 3. **User-Role Assignment System** ✅
**Problem**: No way to assign roles to users programmatically with proper authorization.

**Solution**: Added secure role assignment endpoints:

**Endpoint**: `POST /api/auth/assign-roles`
- Protected with `@HasPermission("user:assign_roles")`
- Accepts `UserRoleAssignDto` with userId and roleIds
- Validates all roles exist before assignment
- Transactional operation

**Also Added**:
- `GET /api/auth/{userId}` - Get user by ID (requires `user:read` permission)

**Files Created**:
- `UserRoleAssignDto.java` - DTO for role assignment

**Files Modified**:
- `UserService.java` - Added `assignRoles()` and `getUserById()` methods
- `UserController.java` - Added role assignment and user read endpoints

---

### 4. **System-Level Permissions & Roles** ✅
**Problem**: No predefined system permissions for managing the security system itself.

**Solution**: Created comprehensive system permissions and roles:

**System Permissions** (14 total):
- `user:read` - Read user information
- `user:assign_roles` - Assign roles to users
- `role:root` - Full role management access
- `role:create`, `role:update`, `role:read`, `role:search`, `role:delete`
- `permission:root` - Full permission management access
- `permission:create`, `permission:update`, `permission:read`, `permission:search`, `permission:delete`

**System Roles** (3 predefined):
1. **SUPER_ADMIN** - Full system access (all 14 permissions)
2. **USER_MANAGER** - Can read users and assign roles
3. **ROLE_MANAGER** - Can manage roles (CRUD operations)

**Files Created**:
- `src/main/resources/security/system/permissions.json`
- `src/main/resources/security/system/roles.json`
- `SystemPermission.java` - Java constants for permissions
- `SystemRole.java` - Java constants for roles

---

### 5. **Frontend Authorization Context** ✅
**Problem**: Frontend needs structured permission/role data for UI access control.

**Solution**: Added comprehensive authorization context endpoint:

**Endpoint**: `GET /api/auth/authorization-context`
- Returns complete user authorization info
- Structured for easy frontend consumption
- Includes roles with nested permissions
- Flattened permission set for quick checks

**Response Structure**:
```json
{
  "userId": 1,
  "username": "john_doe",
  "name": "John Doe",
  "roles": [
    {
      "roleId": 1,
      "roleName": "SUPER_ADMIN",
      "permissions": [
        {
          "permissionId": 1,
          "name": "user:read",
          "showName": "User Read"
        }
      ]
    }
  ],
  "allPermissions": ["user:read", "user:assign_roles", ...]
}
```

**Frontend Usage Example**:
```javascript
// Check if user has permission
const authContext = await getAuthorizationContext();
const canCreateRole = authContext.allPermissions.includes('role:create');

// Show/hide UI elements based on permissions
if (canCreateRole) {
  // Show "Create Role" button
}
```

**Files Created**:
- `UserAuthorizationDto.java` - Main authorization context DTO
- `RoleAuthDto.java` - Role with permissions DTO
- `PermissionAuthDto.java` - Permission DTO

**Files Modified**:
- `UserService.java` - Added `getAuthorizationContext()` method
- `UserController.java` - Added authorization context endpoint

---

### 6. **Security Bug Fixes** ✅
**Fixed**:
- Removed debug `System.out.println()` from `HasPermissionAspect.java:32`
- This was leaking permission information to console logs

---

## 📋 API Endpoints Summary

### User Management (`/api/auth`)
| Method | Endpoint | Permission Required | Description |
|--------|----------|---------------------|-------------|
| POST | `/register` | None (Public) | Register new user |
| POST | `/login` | None (Public) | User login |
| GET | `/` | Authenticated | Get current user info |
| PUT | `/` | Authenticated | Update current user |
| GET | `/{userId}` | `user:read` | Get user by ID |
| POST | `/assign-roles` | `user:assign_roles` | Assign roles to user |
| GET | `/authorization-context` | Authenticated | Get full auth context |

### Role Management (`/api/role`)
| Method | Endpoint | Permission Required | Description |
|--------|----------|---------------------|-------------|
| POST | `/` | `role:create` | Create role |
| PUT | `/{id}` | `role:update` | Update role |
| GET | `/{id}` | `role:read` | Get role by ID |
| GET | `/` | `role:search` | Search roles |
| DELETE | `/{id}` | `role:delete` | Delete role |

### Permission Management (`/api/permission`)
| Method | Endpoint | Permission Required | Description |
|--------|----------|---------------------|-------------|
| POST | `/` | `permission:create` | Create permission |
| PUT | `/{id}` | `permission:update` | Update permission |
| GET | `/{id}` | `permission:read` | Get permission by ID |
| GET | `/` | `permission:search` | Search permissions |
| DELETE | `/{id}` | `permission:delete` | Delete permission |

---

## 🚀 Setup Instructions

### 1. Database Migration
The system will auto-create tables on startup. Ensure your database is configured properly.

### 2. Initialize System Permissions & Roles
You need to manually insert the system permissions and roles from JSON files:

```bash
# The JSON files are located at:
# - src/main/resources/security/system/permissions.json
# - src/main/resources/security/system/roles.json
```

**Manual SQL Insert** (or create a data loader):
```sql
-- Insert permissions first
INSERT INTO permissions (name, show_name, created_at, updated_at, deleted)
VALUES ('user:read', 'User Read', NOW(), NOW(), false);
-- ... repeat for all 14 permissions

-- Insert roles
INSERT INTO roles (name, created_at, updated_at, deleted)
VALUES ('SUPER_ADMIN', NOW(), NOW(), false);
-- ... repeat for all 3 roles

-- Link roles to permissions via role_permission table
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'SUPER_ADMIN';
-- ... link permissions to each role
```

### 3. Assign Initial Super Admin
After creating your first user, manually assign SUPER_ADMIN role:

```sql
INSERT INTO user_roles (user_id, role_id)
SELECT 1, id FROM roles WHERE name = 'SUPER_ADMIN';
```

---

## 🔐 Security Best Practices Implemented

1. ✅ **Principle of Least Privilege**: All sensitive endpoints require specific permissions
2. ✅ **Secure Password Updates**: Passwords are encrypted before storage
3. ✅ **JWT Token Validation**: All protected endpoints verify JWT tokens
4. ✅ **Transaction Safety**: Database operations use `@Transactional`
5. ✅ **Input Validation**: Jakarta Bean Validation on all DTOs
6. ✅ **Authorization Context**: Frontend receives structured permission data
7. ✅ **No Debug Leaks**: Removed debug logging from production code

---

## 📊 Architecture Improvements

### Before
```
User Controller
├─ register() ✅
├─ login() ✅
└─ get() ✅

Role Controller (NO AUTHORIZATION!) ❌
├─ create()
├─ update()
├─ read()
└─ delete()

Permission Controller (NO AUTHORIZATION!) ❌
├─ create()
├─ update()
├─ read()
└─ delete()
```

### After
```
User Controller
├─ register() ✅
├─ login() ✅
├─ get() ✅
├─ update() ✅ NEW
├─ getUserById() ✅ NEW [@HasPermission("user:read")]
├─ assignRoles() ✅ NEW [@HasPermission("user:assign_roles")]
└─ getAuthorizationContext() ✅ NEW

Role Controller
├─ create() ✅ [@HasPermission("role:create")]
├─ update() ✅ [@HasPermission("role:update")]
├─ read() ✅ [@HasPermission("role:read")]
├─ search() ✅ [@HasPermission("role:search")]
└─ delete() ✅ [@HasPermission("role:delete")]

Permission Controller
├─ create() ✅ [@HasPermission("permission:create")]
├─ update() ✅ [@HasPermission("permission:update")]
├─ read() ✅ [@HasPermission("permission:read")]
├─ search() ✅ [@HasPermission("permission:search")]
└─ delete() ✅ [@HasPermission("permission:delete")]

System Roles & Permissions
├─ SUPER_ADMIN (14 permissions)
├─ USER_MANAGER (2 permissions)
└─ ROLE_MANAGER (5 permissions)
```

---

## 🧪 Testing Recommendations

### 1. Test User Update
```bash
# Update current user
curl -X PUT http://localhost:8080/api/auth \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "New Name", "password": "newPassword123"}'
```

### 2. Test Role Assignment (requires user:assign_roles permission)
```bash
curl -X POST http://localhost:8080/api/auth/assign-roles \
  -H "Authorization: Bearer SUPER_ADMIN_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"userId": 2, "roleIds": [1, 2]}'
```

### 3. Test Authorization Context
```bash
curl -X GET http://localhost:8080/api/auth/authorization-context \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 4. Test Protected Endpoints (should fail without permission)
```bash
# Try to create role without permission (should fail)
curl -X POST http://localhost:8080/api/role \
  -H "Authorization: Bearer REGULAR_USER_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "NEW_ROLE"}'
```

---

## 📝 Code Generator Integration

The code generator already creates permissions and roles for entities:
- Automatically generates CRUD permissions for each entity
- Creates `{ENTITY}_ADMIN` role with all permissions
- Saves to `src/main/resources/security/{entity}/permissions.json`
- Saves to `src/main/resources/security/{entity}/roles.json`

**Example** (for Person entity):
```json
// permissions.json
[
  {"name": "person:root", "showName": "Person Root"},
  {"name": "person:create", "showName": "Person Create"},
  {"name": "person:update", "showName": "Person Update"},
  {"name": "person:read", "showName": "Person Read"},
  {"name": "person:search", "showName": "Person Search"},
  {"name": "person:delete", "showName": "Person Delete"}
]

// roles.json
[
  {
    "name": "PERSON_ADMIN",
    "permissions": ["person:root", "person:create", "person:read",
                    "person:search", "person:update", "person:delete"]
  }
]
```

---

## ✅ Production Readiness Checklist

- [x] User profile update with validation
- [x] Secure password change functionality
- [x] Role & Permission CRUD with authorization
- [x] User-Role assignment system
- [x] System-level permissions & roles
- [x] Frontend authorization context API
- [x] Removed debug/console logging
- [x] All endpoints use proper `@HasPermission` annotations
- [x] Transaction safety on critical operations
- [x] Input validation on all DTOs
- [x] Comprehensive API documentation

---

## 🔄 Migration from Previous Version

If you have existing data:

1. **Backup your database first!**
2. No schema changes required (uses existing tables)
3. Insert system permissions and roles from JSON files
4. Assign SUPER_ADMIN role to at least one user
5. Test all endpoints with proper permissions
6. Update frontend to use `/authorization-context` endpoint

---

## 📚 Additional Resources

- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)
- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)

---

## 🤝 Support

For issues or questions:
1. Check the API documentation at `/swagger-ui.html`
2. Review the permission requirements in this document
3. Verify JWT token is valid and not expired
4. Ensure user has required permissions assigned

---

**Last Updated**: 2025-10-03
**Author**: Security Enhancement Team
**Version**: 2.0.0
