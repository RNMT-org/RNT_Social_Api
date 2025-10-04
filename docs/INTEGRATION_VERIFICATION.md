# Integration Verification Guide

## Core Package Integration

The `core` package in `rnt-social-api-main` contains all core functionality and is automatically integrated:

### ✅ Component Scanning
```java
@SpringBootApplication  // Auto-scans: ir.rayanovinmt.rnt_social_api.**
public class RntSocialApiApplication {
    // No custom @ComponentScan needed
}
```

**This automatically includes:**
- `ir.rayanovinmt.rnt_social_api.core.**` (all core packages)
- `ir.rayanovinmt.rnt_social_api.person.**`
- `ir.rayanovinmt.rnt_social_api.post.**`
- Generated entities (category, product, order, etc.)

### ✅ Core Endpoints Available

#### Security & User Management
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login (returns JWT)
- `GET /api/users/me` - Current user info
- `GET /api/users` - List users
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Roles & Permissions
- `GET /api/roles` - List all roles
- `POST /api/roles` - Create role
- `GET /api/permissions` - List all permissions
- `POST /api/permissions` - Create permission

#### Document Management
- `POST /api/documents/upload` - Upload file
- `GET /api/documents/{id}` - Get document
- `DELETE /api/documents/{id}` - Delete document

#### Notifications
- `GET /api/notifications` - Get user notifications
- `POST /api/notifications/send` - Send notification
- `PUT /api/notifications/{id}/read` - Mark as read

#### Job Execution (Quartz)
- `GET /api/job-executions` - List job executions
- `GET /api/job-executions/{id}` - Get job details

#### Async Operations
- `GET /api/async/status` - Check async operation status
- `GET /api/async/metrics` - Get async metrics

#### Test/Health
- `GET /api/test/hello` - Hello endpoint (public)

### ✅ Database Configuration

**Automatic JPA Scanning:**
```yaml
# application.yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/rnt_social
    username: postgres
    password: 12345678
  jpa:
    hibernate:
      ddl-auto: update  # Auto-creates tables
    show-sql: false
```

**Auto-discovered Entities:**
- Core entities: `User`, `Role`, `Permission`, `Document`, `Notification`, `LogEntity`, `JobExecutionEntity`
- Hand-written: `PersonEntity`, `PostEntity`, `TagEntity`
- Generated: `ProductEntity`, `CategoryEntity`, `OrderEntity`, etc.

### ✅ Swagger UI Integration

**Access:** `http://localhost:8080/swagger-ui.html`

**Configuration:**
```java
@Configuration
@SecurityScheme(name = "bearerAuth", type = HTTP, scheme = "bearer")
public class SwaggerConfig {
    // Auto-discovers ALL controllers in ir.rayanovinmt.rnt_social_api.**
}
```

**Includes:**
- ✅ Core controllers (User, Role, Permission, Document, etc.)
- ✅ Hand-written controllers (Person, Post, Tag)
- ✅ Generated controllers (Product, Category, Order, etc.)

All grouped by tags in Swagger UI.

## Verification Steps

### 1. Build & Run
```bash
# From project root
mvn clean install
cd rnt-social-api-main
mvn spring-boot:run
```

### 2. Check Swagger UI
Open: `http://localhost:8080/swagger-ui.html`

**Verify sections exist:**
- 📁 user-controller
- 📁 role-controller
- 📁 permission-controller
- 📁 document-controller
- 📁 notification-controller
- 📁 person-controller
- 📁 post-controller
- 📁 tag-controller
- 📁 product-controller (generated)
- 📁 category-controller (generated)
- 📁 order-controller (generated)

### 3. Test Core Endpoint
```bash
# Hello endpoint (public)
curl http://localhost:8080/api/test/hello

# Expected: {"message": "Hello World!"}
```

### 4. Test Auth Flow
```bash
# Register user
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"password123","email":"test@example.com"}'

# Login
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"password123"}'

# Returns: {"token": "eyJhbGc..."}
```

### 5. Test Generated Endpoint
```bash
# Get products (needs auth token)
curl http://localhost:8080/api/products \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Configuration Files

### Database (PostgreSQL)
```yaml
# rnt-social-api-main/src/main/resources/application.yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/rnt_social
    username: postgres
    password: 12345678
```

### Redis (Caching)
```yaml
spring:
  data:
    redis:
      url: redis://:12345678@localhost:6379/0
```

### JWT
```yaml
jwt:
  token: ac7b9c76236ae1e80f181586a980d292a1a6eb14e3beeaa97d01523ef2a571b1
```

## Troubleshooting

### Endpoints Not Showing in Swagger
**Cause:** Controllers not in scanned package
**Solution:** Ensure controllers are under `ir.rayanovinmt.rnt_social_api.**`

### Database Connection Error
**Cause:** PostgreSQL not running or wrong credentials
**Solution:**
```bash
# Check PostgreSQL
psql -U postgres -d rnt_social

# Or start Docker
docker-compose up -d
```

### Generated Code Missing
**Cause:** Maven didn't run code generation
**Solution:**
```bash
mvn clean compile  # Forces regeneration
```

### 401 Unauthorized on Protected Endpoints
**Cause:** Missing or invalid JWT token
**Solution:**
1. Call `/api/users/login` to get token
2. Add header: `Authorization: Bearer YOUR_TOKEN`

## Security Configuration

**Public Endpoints (no auth):**
- `/api/test/**`
- `/api/users/register`
- `/api/users/login`
- `/swagger-ui/**`
- `/v3/api-docs/**`

**Protected Endpoints (requires JWT):**
- All other `/api/**` endpoints

**Role-Based Access:**
```java
@HasRole("ADMIN")
public void adminOnlyMethod() { }

@HasPermission("PRODUCT_CREATE")
public void createProduct() { }
```

## Module Dependencies

```
rnt-core (utils only)
    ↑
    ├── code-generator
    │
    └── rnt-social-api-main
            ↓
        ALL features available:
        - Core endpoints ✅
        - Generated endpoints ✅
        - Database ✅
        - Swagger ✅
        - Security ✅
```

## Summary

✅ **Core package is fully integrated**
- No special configuration needed
- Auto-discovered by Spring Boot
- All endpoints available in Swagger
- Database entities auto-created
- Security applies to all endpoints

✅ **Generated code integrates seamlessly**
- Same package structure
- Same security model
- Same Swagger documentation
- Same database schema

✅ **Configuration is centralized**
- Single `application.yaml`
- Environment variables supported
- Docker-friendly

The application is a **unified system** where core features, hand-written code, and generated code work together transparently.
