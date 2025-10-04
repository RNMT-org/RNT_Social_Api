# RNT Social API - Multi-Module Maven Project

A professional Spring Boot application with automated code generation from XML definitions.

## 📁 Project Structure

```
RNT_Social_Api/
├── pom.xml                    # Parent POM
├── rnt-core/                  # Shared utilities (no Spring deps)
│   └── src/main/java/ir/rayanovinmt/core/util/
│       └── StringUtils.java
├── code-generator/            # Standalone code generator
│   ├── pom.xml
│   └── src/main/
│       ├── java/ir/rayanovinmt/codegen/
│       │   ├── XmlToCgConverter.java    # Step 1: XML → .cg
│       │   ├── CgToCodeGenerator.java   # Step 2: .cg → Java
│       │   └── model/                   # Data models
│       └── resources/templates/         # FreeMarker templates
│           ├── EntityTemplate.ftl
│           ├── DtoTemplate.ftl
│           ├── MapperTemplate.ftl
│           └── ...
├── rnt-social-api-main/       # Main Spring Boot application
│   ├── pom.xml
│   └── src/main/
│       ├── java/ir/rayanovinmt/rnt_social_api/
│       │   ├── core/                    # Core Spring features
│       │   ├── person/                  # Hand-written entities
│       │   ├── post/
│       │   ├── tag/
│       │   └── [generated]/             # Auto-generated (clean removes)
│       └── resources/
│           └── generate-xmls/           # XML definitions
│               ├── Person.xml
│               ├── Post.xml
│               └── .cg/                 # Generated intermediate files
└── docs/                      # Documentation
    ├── README.md
    ├── CODE_GENERATION_GUIDE.md
    └── ...
```

## 🚀 Quick Start

### Build Everything
```bash
mvn clean install
```

This automatically:
1. ✅ Builds `rnt-core` module
2. ✅ Builds `code-generator` module
3. ✅ Generates .cg files from XMLs (Step 1)
4. ✅ Generates Java code from .cg files (Step 2)
5. ✅ Compiles main application with generated code
6. ✅ Runs tests
7. ✅ Creates JARs

### Run Application
```bash
cd rnt-social-api-main
mvn spring-boot:run
```

### Clean Generated Code
```bash
mvn clean  # Removes all generated entities, DTOs, mappers, etc.
```

## 📝 Code Generation Workflow

### Define Entity (XML)
Create `rnt-social-api-main/src/main/resources/generate-xmls/Product.xml`:

```xml
<entity name="Product" tableName="products" generate="true">
    <fields>
        <field name="name" type="String" required="true" maxLength="100"/>
        <field name="price" type="BigDecimal" required="true"/>
        <field name="status" type="Enum">
            <enums>
                <enum name="ACTIVE" value="active"/>
                <enum name="INACTIVE" value="inactive"/>
            </enums>
        </field>
    </fields>
</entity>
```

### Build
```bash
mvn compile
```

### Generated Files
- ✅ `Product Entity.java`
- ✅ `ProductCreateDto.java`, `ProductUpdateDto.java`, `ProductLoadDto.java`
- ✅ `ProductMapper.java`
- ✅ `ProductRepository.java`
- ✅ `ProductService.java`
- ✅ `ProductController.java`
- ✅ `ProductStatusEnum.java`

## 🏗️ Module Dependencies

```
rnt-core (no deps)
    ↑
    ├── code-generator (FreeMarker, Jackson, Lombok)
    │
    └── rnt-social-api-main (Spring Boot, JPA, etc.)
            ↑ (provided scope)
            └── code-generator
```

## 🔧 Maven Commands

| Command | Description |
|---------|-------------|
| `mvn clean` | Remove generated code & build artifacts |
| `mvn compile` | Generate code & compile |
| `mvn install` | Full build + install to local Maven repo |
| `mvn test` | Run tests |
| `mvn spring-boot:run` | Run application (in rnt-social-api-main) |

## 📦 Modules

### rnt-core
- Shared utilities with **no external dependencies**
- Used by both code-generator and main app
- Contains: StringUtils, etc.

### code-generator
- Standalone code generator
- Dependencies: FreeMarker, Jackson, Lombok, SLF4J
- Can be reused in other projects
- Two-step generation:
  - **Step 1**: XML → .cg (intermediate YAML format)
  - **Step 2**: .cg → Java code

### rnt-social-api-main
- Spring Boot application
- Contains hand-written core functionality
- Generated code is added during build
- Clean removes all generated files

## 🎯 Benefits

✅ **No circular dependencies** - Generator is separate
✅ **Clean builds** - `mvn clean` removes generated code
✅ **Standard Maven** - No shell scripts needed
✅ **Modular** - Core utilities shared properly
✅ **Automated** - Code generation happens during build
✅ **Type-safe** - Generated code compiles with main code

## 📚 Documentation

See `docs/` folder for:
- Code generation guide
- Template customization
- Security setup
- And more...

## 🔒 Security

Generated code includes:
- Role-based access control (RBAC)
- Permission system
- JWT authentication ready
- Security annotations

## 🛠️ Development

1. Edit XML definitions in `rnt-social-api-main/src/main/resources/generate-xmls/`
2. Run `mvn compile`
3. Generated code appears in `rnt-social-api-main/src/main/java/.../[entity-name]/`
4. Hand-write business logic in core packages
5. Generated code is gitignored, XML definitions are source of truth

## 📖 Template Customization

Templates are in `code-generator/src/main/resources/templates/`:
- `EntityTemplate.ftl` - JPA entities
- `DtoTemplate.ftl` - DTOs with validation
- `MapperTemplate.ftl` - MapStruct mappers
- `ServiceTemplate.ftl` - Service layer
- `ControllerTemplate.ftl` - REST controllers

Modify templates to change generated code style.

---

**Built with:** Java 21, Spring Boot 3.4.2, Maven, FreeMarker, MapStruct
