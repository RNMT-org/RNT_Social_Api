# Code Generation Guide - Two-Step Process

## 🎯 Overview

This project uses a **two-step code generation system**:

1. **XML → .cg**: Convert XML entity definitions to intermediate YAML files (.cg)
2. **.cg → Java**: Generate complete CRUD code from .cg files

This allows you to review and manually edit the intermediate `.cg` files before final code generation.

---

## 📋 Quick Start Commands

### Option 1: Using Shell Scripts (Recommended)

```bash
# Step 1: XML → .cg
./generate-step1.sh

# Step 2: .cg → Java Code
./generate-step2.sh

# Step 3: Compile everything
mvn clean compile
```

### Option 2: Using Maven Directly

```bash
# Ensure Java 21 is being used
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Step 1: Compile project first
mvn compile -DskipTests

# Step 2: XML → .cg
mvn exec:java \
  -Dexec.mainClass="ir.rayanovinmt.rnt_social_api.core.generator.XmlToCgConverter" \
  -Dexec.args="generate-xmls" \
  -Dexec.classpathScope=compile

# Step 3: .cg → Java Code
mvn exec:java \
  -Dexec.mainClass="ir.rayanovinmt.rnt_social_api.core.generator.CgToCodeGenerator" \
  -Dexec.args="generate-xmls" \
  -Dexec.classpathScope=compile

# Step 4: Compile generated code
mvn clean compile
```

---

## 🔧 How It Works

### Step 1: XML → .cg Conversion

**Input**: `src/main/resources/generate-xmls/*.xml`
**Output**: `src/main/resources/generate-xmls/.cg/*.cg`

**What it does:**
- Parses XML entity definitions
- Validates entity structure
- Converts to YAML format (.cg files)
- Stores in `.cg` subfolder

**Example output:**
```
╔════════════════════════════════════════════════════════════════╗
║              STEP 1: XML → .cg Conversion                      ║
╚════════════════════════════════════════════════════════════════╝

✅ Product.xml → Product.cg
✅ Category.xml → Category.cg
✅ Order.xml → Order.cg
...

STEP 1 COMPLETE: 8 .cg files generated
📁 Generated .cg files: src/main/resources/generate-xmls/.cg/
```

### Step 2: .cg → Java Code Generation

**Input**: `src/main/resources/generate-xmls/.cg/*.cg`
**Output**: Complete CRUD code for each entity

**What it generates (per entity):**

```
src/main/java/{basePackage}/{entityName}/
├── {Entity}Entity.java           # JPA Entity
├── {Entity}Controller.java        # REST Controller with @HasPermission
├── {Entity}Service.java           # Service layer
├── {Entity}Repository.java        # JPA Repository
├── {Entity}Mapper.java            # MapStruct mapper
├── dto/
│   ├── {Entity}CreateDto.java    # Create DTO with validation
│   ├── {Entity}UpdateDto.java    # Update DTO with validation
│   └── {Entity}LoadDto.java      # Load DTO (no validation)
└── constant/
    ├── {Entity}Permission.java    # Permission constants
    ├── {Entity}Role.java          # Role constants
    └── {Entity}*Enum.java         # Enums (if any)

src/main/resources/security/{entityName}/
├── permissions.json               # 6 CRUD permissions
└── roles.json                     # ADMIN role
```

**Example output:**
```
╔════════════════════════════════════════════════════════════════╗
║              STEP 2: .cg → Java Code Generation                ║
╚════════════════════════════════════════════════════════════════╝

📦 Generating: Product
   ✅ Complete

📦 Generating: Category
   ✅ Complete

STEP 2 COMPLETE: Code generation successful
```

---

## 📦 What Gets Generated

### For Entity: `Product`

**Generated Files (13+):**
1. `ProductEntity.java` - JPA entity with relationships, validations, indexes
2. `ProductController.java` - REST API with 5 endpoints
3. `ProductService.java` - Business logic layer
4. `ProductRepository.java` - Data access layer
5. `ProductMapper.java` - MapStruct DTO↔Entity mapper
6. `ProductCreateDto.java` - Creation DTO with Jakarta validation
7. `ProductUpdateDto.java` - Update DTO with Jakarta validation
8. `ProductLoadDto.java` - Load DTO with relationships
9. `ProductPermission.java` - Permission constants (6 permissions)
10. `ProductRole.java` - Role constants
11. `ProductStatusEnum.java` - Enum classes (if defined)
12. `permissions.json` - Permission definitions
13. `roles.json` - Role definitions with permissions

### REST API Endpoints Generated

For each entity, these endpoints are created:

```java
POST   /api/product        # Create new product (requires product:create)
PUT    /api/product/{id}   # Update product (requires product:update)
GET    /api/product/{id}   # Get by ID (requires product:read)
GET    /api/product         # Search products (requires product:search)
DELETE /api/product/{id}   # Delete product (requires product:delete)
```

All endpoints are:
- ✅ Protected with `@HasPermission` annotation
- ✅ Documented with Swagger (`@Operation`)
- ✅ Validated with Jakarta Bean Validation
- ✅ Use DTOs (not entities) for requests/responses
- ✅ Support soft delete
- ✅ Include search functionality

---

## 🎨 Customization Workflow

### 1. Create XML Entity

Create `src/main/resources/generate-xmls/Product.xml`:

```xml
<entity>
    <name>Product</name>
    <generate>true</generate>
    <fields>
        <field>
            <name>name</name>
            <type>STRING</type>
            <required>true</required>
            <maxLength>100</maxLength>
            <searchable>true</searchable>
        </field>
        <field>
            <name>price</name>
            <type>BIG_DECIMAL</type>
            <required>true</required>
            <precision>10</precision>
            <scale>2</scale>
            <positive>true</positive>
        </field>
    </fields>
</entity>
```

### 2. Run Step 1

```bash
./generate-step1.sh
```

### 3. Review .cg File (Optional)

Check `src/main/resources/generate-xmls/.cg/Product.cg`:

```yaml
name: "Product"
generate: true
fields:
  - name: "name"
    type: "STRING"
    required: true
    maxLength: 100
    searchable: true
  - name: "price"
    type: "BIG_DECIMAL"
    required: true
    precision: 10
    scale: 2
    positive: true
```

**You can manually edit this .cg file if needed!**

### 4. Run Step 2

```bash
./generate-step2.sh
```

### 5. Compile & Run

```bash
mvn clean compile
mvn spring-boot:run
```

### 6. Test API

Navigate to: `http://localhost:8080/swagger-ui.html`

---

## 🔍 Example: Complete Workflow

```bash
# 1. Create XML entity definition
cat > src/main/resources/generate-xmls/Article.xml <<EOF
<entity>
    <name>Article</name>
    <generate>true</generate>
    <fields>
        <field>
            <name>title</name>
            <type>STRING</type>
            <required>true</required>
            <maxLength>200</maxLength>
            <searchable>true</searchable>
        </field>
        <field>
            <name>content</name>
            <type>STRING</type>
            <required>true</required>
            <length>5000</length>
        </field>
    </fields>
</entity>
EOF

# 2. Generate .cg file
./generate-step1.sh

# 3. (Optional) Edit .cg file if needed
# vim src/main/resources/generate-xmls/.cg/Article.cg

# 4. Generate Java code
./generate-step2.sh

# 5. Compile
mvn clean compile

# 6. Run application
mvn spring-boot:run

# 7. Test endpoints
curl -X POST http://localhost:8080/api/article \
  -H "Authorization: Bearer YOUR_JWT" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Article",
    "content": "This is the content..."
  }'
```

---

## 🚨 Troubleshooting

### Issue: `ClassNotFoundException`

**Cause**: Project not compiled before running generator
**Solution**:
```bash
mvn compile -DskipTests
```

### Issue: No .cg files generated

**Check**:
1. XML files exist in `src/main/resources/generate-xmls/`
2. XML syntax is valid
3. `<generate>true</generate>` is set

### Issue: Generated code doesn't compile

**Common fixes**:
1. Clean before compiling: `mvn clean compile`
2. Delete `.cg` folder and regenerate: `rm -rf src/main/resources/generate-xmls/.cg`
3. Check XML field types are valid (see CODE_GENERATOR_GUIDE.md)

### Issue: Permission denied errors

**Solution**:
```bash
chmod +x generate-step1.sh generate-step2.sh
```

---

## 📚 Additional Resources

- **Complete XML Reference**: `CODE_GENERATOR_GUIDE.md`
- **Security Setup**: `COMPLETE_SETUP_GUIDE.md`
- **Security Enhancements**: `SECURITY_IMPROVEMENTS.md`

---

## ✅ Summary

| Step | Command | Input | Output |
|------|---------|-------|--------|
| 1 | `./generate-step1.sh` | XML files | .cg YAML files |
| 2 | `./generate-step2.sh` | .cg files | Java code (13+ files per entity) |
| 3 | `mvn clean compile` | Java code | Compiled classes |
| 4 | `mvn spring-boot:run` | Compiled code | Running application |

**That's it! You now have a complete CRUD API with security, validation, and Swagger docs.** 🎉
