# Code Generator - Complete Guide

## Overview
The Code Generator is a powerful tool that generates complete CRUD operations, DTOs, controllers, services, repositories, and permissions/roles from XML definitions. It supports advanced features like validation, relationships, indexes, and custom constraints.

---

## 📁 File Structure

```
src/main/resources/
├── generate-xmls/          # Place your XML entity definitions here
│   ├── Person.xml
│   ├── Post.xml
│   └── Tag.xml
└── generators/              # FreeMarker templates
    ├── EntityTemplate.ftl
    ├── ControllerTemplate.ftl
    ├── ServiceTemplate.ftl
    ├── RepositoryTemplate.ftl
    ├── DtoTemplate.ftl
    ├── MapperTemplate.ftl
    ├── PermissionTemplate.ftl
    └── RoleTemplate.ftl
```

---

## 📝 XML Schema Reference

### Basic Entity Structure

```xml
<entity>
    <name>EntityName</name>
    <generate>true</generate>
    <fields>
        <!-- Field definitions -->
    </fields>
    <relationships>
        <!-- Relationship definitions -->
    </relationships>
</entity>
```

---

## 🔧 Field Configuration

### Basic Field

```xml
<field>
    <name>fieldName</name>
    <type>STRING</type>
    <searchable>true</searchable>
</field>
```

### Field Types

| Type | Java Type | Import Required |
|------|-----------|-----------------|
| `STRING` | String | No |
| `INTEGER` | Integer | No |
| `LONG` | Long | No |
| `BOOLEAN` | Boolean | No |
| `DOUBLE` | Double | No |
| `DATE` | Date | java.util.Date |
| `LOCAL_DATE` | LocalDate | java.time.LocalDate |
| `LOCAL_DATE_TIME` | LocalDateTime | java.time.LocalDateTime |
| `BIG_DECIMAL` | BigDecimal | java.math.BigDecimal |
| `ENUM` | Enum | Generated |

---

## 🎯 Validation Attributes

### Required & Null Checks

```xml
<field>
    <name>email</name>
    <type>STRING</type>
    <required>true</required>           <!-- @NotNull on entity, DTOs -->
    <notBlank>true</notBlank>          <!-- @NotBlank on DTOs -->
    <notEmpty>true</notEmpty>          <!-- @NotEmpty on DTOs -->
</field>
```

### String Validation

```xml
<field>
    <name>username</name>
    <type>STRING</type>
    <minLength>3</minLength>           <!-- @Size(min=3) -->
    <maxLength>50</maxLength>          <!-- @Size(max=50) -->
    <pattern>^[a-zA-Z0-9_]+$</pattern> <!-- @Pattern -->
</field>
```

### Email Validation

```xml
<field>
    <name>email</name>
    <type>STRING</type>
    <email>true</email>                <!-- @Email -->
    <notBlank>true</notBlank>
</field>
```

### Numeric Validation

```xml
<field>
    <name>age</name>
    <type>INTEGER</type>
    <min>18</min>                      <!-- @Min(18) -->
    <max>120</max>                     <!-- @Max(120) -->
    <positive>true</positive>          <!-- @Positive -->
</field>

<field>
    <name>balance</name>
    <type>BIG_DECIMAL</type>
    <positiveOrZero>true</positiveOrZero>  <!-- @PositiveOrZero -->
</field>

<field>
    <name>temperature</name>
    <type>DOUBLE</type>
    <negative>true</negative>          <!-- @Negative -->
</field>
```

### Date Validation

```xml
<field>
    <name>birthDate</name>
    <type>LOCAL_DATE</type>
    <past>true</past>                  <!-- @Past -->
</field>

<field>
    <name>appointmentDate</name>
    <type>LOCAL_DATE_TIME</type>
    <future>true</future>              <!-- @Future -->
</field>
```

---

## 📊 Column Customization

### Column Properties

```xml
<field>
    <name>username</name>
    <type>STRING</type>
    <unique>true</unique>               <!-- Creates unique constraint -->
    <length>100</length>                <!-- Column length -->
    <columnName>user_name</columnName>  <!-- Custom column name -->
    <defaultValue>guest</defaultValue>  <!-- Default value -->
</field>
```

### Decimal Precision

```xml
<field>
    <name>price</name>
    <type>BIG_DECIMAL</type>
    <precision>10</precision>           <!-- Total digits -->
    <scale>2</scale>                    <!-- Decimal places -->
</field>
```

### Indexes

```xml
<field>
    <name>email</name>
    <type>STRING</type>
    <indexed>true</indexed>             <!-- Creates index -->
    <indexName>idx_user_email</indexName>  <!-- Custom index name (optional) -->
</field>
```

---

## 🔗 Relationships

### One-to-One

```xml
<relationship>
    <relationshipType>ONE_TO_ONE</relationshipType>
    <relatedEntityName>Profile</relatedEntityName>
    <cascade>ALL</cascade>
    <fetch>LAZY</fetch>
    <orphanRemoval>true</orphanRemoval>
    <optional>false</optional>
</relationship>
```

### One-to-Many (Bidirectional)

```xml
<!-- Parent Side -->
<relationship>
    <relationshipType>ONE_TO_MANY</relationshipType>
    <relatedEntityName>Comment</relatedEntityName>
    <mappedBy>post</mappedBy>           <!-- Field name in Comment entity -->
    <cascade>ALL</cascade>
    <orphanRemoval>true</orphanRemoval>
</relationship>

<!-- Child Side -->
<relationship>
    <relationshipType>MANY_TO_ONE</relationshipType>
    <relatedEntityName>Post</relatedEntityName>
    <name>post</name>                   <!-- Custom field name -->
    <required>true</required>           <!-- NOT NULL foreign key -->
    <fetch>EAGER</fetch>
</relationship>
```

### Many-to-One

```xml
<relationship>
    <relationshipType>MANY_TO_ONE</relationshipType>
    <relatedEntityName>Category</relatedEntityName>
    <required>true</required>
    <joinColumnName>category_id</joinColumnName>  <!-- Custom FK name -->
    <referencedColumnName>id</referencedColumnName>
</relationship>
```

### Many-to-Many

```xml
<!-- Owning Side -->
<relationship>
    <relationshipType>MANY_TO_MANY</relationshipType>
    <relatedEntityName>Tag</relatedEntityName>
    <joinTableName>post_tag</joinTableName>       <!-- Custom join table -->
    <inverseJoinColumnName>tag_id</inverseJoinColumnName>
    <searchable>true</searchable>
</relationship>

<!-- Inverse Side -->
<relationship>
    <relationshipType>MANY_TO_MANY</relationshipType>
    <relatedEntityName>Post</relatedEntityName>
    <mappedBy>tags</mappedBy>
</relationship>
```

### Cascade Types

Available cascade options (comma-separated for multiple):
- `ALL` - All operations
- `PERSIST` - Insert operations
- `MERGE` - Update operations
- `REMOVE` - Delete operations
- `REFRESH` - Refresh operations
- `DETACH` - Detach operations

```xml
<cascade>PERSIST,MERGE</cascade>  <!-- Multiple cascades -->
```

### Fetch Types

- `LAZY` - Lazy loading (default)
- `EAGER` - Eager loading

```xml
<fetch>EAGER</fetch>
```

---

## 🎨 Enum Fields

```xml
<field>
    <name>status</name>
    <type>ENUM</type>
    <required>true</required>
    <enums>
        <enum>
            <name>ACTIVE</name>
            <value>Active</value>
        </enum>
        <enum>
            <name>INACTIVE</name>
            <value>Inactive</value>
        </enum>
        <enum>
            <name>PENDING</name>
            <value>Pending</value>
        </enum>
    </enums>
</field>
```

---

## 📋 Complete Examples

### Example 1: User Entity (Advanced)

```xml
<entity>
    <name>User</name>
    <generate>true</generate>
    <fields>
        <field>
            <name>username</name>
            <type>STRING</type>
            <required>true</required>
            <notBlank>true</notBlank>
            <unique>true</unique>
            <minLength>3</minLength>
            <maxLength>50</maxLength>
            <pattern>^[a-zA-Z0-9_]+$</pattern>
            <indexed>true</indexed>
            <searchable>true</searchable>
        </field>

        <field>
            <name>email</name>
            <type>STRING</type>
            <required>true</required>
            <notBlank>true</notBlank>
            <unique>true</unique>
            <email>true</email>
            <maxLength>255</maxLength>
            <indexed>true</indexed>
            <searchable>true</searchable>
        </field>

        <field>
            <name>password</name>
            <type>STRING</type>
            <required>true</required>
            <notBlank>true</notBlank>
            <minLength>8</minLength>
        </field>

        <field>
            <name>age</name>
            <type>INTEGER</type>
            <min>18</min>
            <max>120</max>
        </field>

        <field>
            <name>accountBalance</name>
            <type>BIG_DECIMAL</type>
            <precision>10</precision>
            <scale>2</scale>
            <positiveOrZero>true</positiveOrZero>
            <defaultValue>0.00</defaultValue>
        </field>

        <field>
            <name>status</name>
            <type>ENUM</type>
            <required>true</required>
            <enums>
                <enum>
                    <name>ACTIVE</name>
                    <value>Active</value>
                </enum>
                <enum>
                    <name>SUSPENDED</name>
                    <value>Suspended</value>
                </enum>
                <enum>
                    <name>BANNED</name>
                    <value>Banned</value>
                </enum>
            </enums>
        </field>
    </fields>

    <relationships>
        <relationship>
            <relationshipType>ONE_TO_MANY</relationshipType>
            <relatedEntityName>Post</relatedEntityName>
            <mappedBy>author</mappedBy>
            <cascade>ALL</cascade>
            <orphanRemoval>true</orphanRemoval>
        </relationship>
    </relationships>
</entity>
```

### Example 2: Product Entity

```xml
<entity>
    <name>Product</name>
    <generate>true</generate>
    <fields>
        <field>
            <name>name</name>
            <type>STRING</type>
            <required>true</required>
            <notBlank>true</notBlank>
            <maxLength>200</maxLength>
            <searchable>true</searchable>
        </field>

        <field>
            <name>sku</name>
            <type>STRING</type>
            <required>true</required>
            <unique>true</unique>
            <maxLength>50</maxLength>
            <indexed>true</indexed>
        </field>

        <field>
            <name>description</name>
            <type>STRING</type>
            <length>1000</length>
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

        <field>
            <name>stock</name>
            <type>INTEGER</type>
            <required>true</required>
            <positiveOrZero>true</positiveOrZero>
            <defaultValue>0</defaultValue>
        </field>

        <field>
            <name>active</name>
            <type>BOOLEAN</type>
            <defaultValue>true</defaultValue>
        </field>
    </fields>

    <relationships>
        <relationship>
            <relationshipType>MANY_TO_ONE</relationshipType>
            <relatedEntityName>Category</relatedEntityName>
            <required>true</required>
            <fetch>EAGER</fetch>
        </relationship>

        <relationship>
            <relationshipType>MANY_TO_MANY</relationshipType>
            <relatedEntityName>Tag</relatedEntityName>
            <joinTableName>product_tags</joinTableName>
            <searchable>true</searchable>
        </relationship>
    </relationships>
</entity>
```

### Example 3: Blog Post with Comments

```xml
<!-- Post.xml -->
<entity>
    <name>Post</name>
    <generate>true</generate>
    <fields>
        <field>
            <name>title</name>
            <type>STRING</type>
            <required>true</required>
            <notBlank>true</notBlank>
            <maxLength>255</maxLength>
            <searchable>true</searchable>
        </field>

        <field>
            <name>content</name>
            <type>STRING</type>
            <required>true</required>
            <length>5000</length>
            <searchable>true</searchable>
        </field>

        <field>
            <name>publishDate</name>
            <type>LOCAL_DATE_TIME</type>
        </field>

        <field>
            <name>viewCount</name>
            <type>LONG</type>
            <defaultValue>0</defaultValue>
            <positiveOrZero>true</positiveOrZero>
        </field>
    </fields>

    <relationships>
        <relationship>
            <relationshipType>MANY_TO_ONE</relationshipType>
            <relatedEntityName>Person</relatedEntityName>
            <name>author</name>
            <required>true</required>
        </relationship>

        <relationship>
            <relationshipType>ONE_TO_MANY</relationshipType>
            <relatedEntityName>Comment</relatedEntityName>
            <mappedBy>post</mappedBy>
            <cascade>ALL</cascade>
            <orphanRemoval>true</orphanRemoval>
        </relationship>

        <relationship>
            <relationshipType>MANY_TO_MANY</relationshipType>
            <relatedEntityName>Tag</relatedEntityName>
            <searchable>true</searchable>
        </relationship>
    </relationships>
</entity>
```

---

## 🔄 Generated Code Structure

For each entity, the generator creates:

```
src/main/java/{basePackage}/{entityName}/
├── {Entity}Entity.java           # JPA Entity with all annotations
├── {Entity}Controller.java        # REST Controller with @HasPermission
├── {Entity}Service.java           # Service with CRUD operations
├── {Entity}Repository.java        # JPA Repository
├── {Entity}Mapper.java            # MapStruct mapper
├── dto/
│   ├── {Entity}CreateDto.java    # DTO with validation for creation
│   ├── {Entity}UpdateDto.java    # DTO with validation for updates
│   └── {Entity}LoadDto.java      # DTO for loading/reading
└── constant/
    ├── {Entity}Permission.java    # Permission constants
    ├── {Entity}Role.java          # Role constants
    └── {Entity}{Field}Enum.java   # Enum classes (if any)

src/main/resources/security/{entityName}/
├── permissions.json               # Permission definitions
└── roles.json                     # Role definitions
```

---

## 🎯 Field Options Quick Reference

| Option | Type | Description | Example |
|--------|------|-------------|---------|
| `name` | String | Field name | `username` |
| `type` | FieldType | Data type | `STRING`, `INTEGER` |
| `required` | Boolean | NOT NULL constraint + @NotNull | `true` |
| `unique` | Boolean | Unique constraint | `true` |
| `searchable` | Boolean | Include in search | `true` |
| `indexed` | Boolean | Create database index | `true` |
| `indexName` | String | Custom index name | `idx_email` |
| `length` | Integer | String/column length | `255` |
| `precision` | Integer | Decimal precision | `10` |
| `scale` | Integer | Decimal scale | `2` |
| `columnName` | String | Custom column name | `user_email` |
| `defaultValue` | String | Default value | `0` |
| `min` | Long | Minimum value (@Min) | `18` |
| `max` | Long | Maximum value (@Max) | `120` |
| `minLength` | Integer | Min string length (@Size) | `3` |
| `maxLength` | Integer | Max string length (@Size) | `50` |
| `pattern` | String | Regex pattern (@Pattern) | `^[A-Z]+$` |
| `email` | Boolean | Email validation (@Email) | `true` |
| `notBlank` | Boolean | Not blank (@NotBlank) | `true` |
| `notEmpty` | Boolean | Not empty (@NotEmpty) | `true` |
| `positive` | Boolean | Positive number (@Positive) | `true` |
| `negative` | Boolean | Negative number (@Negative) | `true` |
| `positiveOrZero` | Boolean | >= 0 (@PositiveOrZero) | `true` |
| `negativeOrZero` | Boolean | <= 0 (@NegativeOrZero) | `true` |
| `future` | Boolean | Future date (@Future) | `true` |
| `past` | Boolean | Past date (@Past) | `true` |

---

## 🔗 Relationship Options Quick Reference

| Option | Type | Description | Example |
|--------|------|-------------|---------|
| `relationshipType` | RelationType | Type of relationship | `MANY_TO_ONE` |
| `relatedEntityName` | String | Target entity name | `Category` |
| `name` | String | Custom field name | `author` |
| `mappedBy` | String | Inverse side field name | `posts` |
| `required` | Boolean | NOT NULL foreign key | `true` |
| `searchable` | Boolean | Include in search | `true` |
| `cascade` | String | Cascade operations | `ALL`, `PERSIST,MERGE` |
| `orphanRemoval` | Boolean | Remove orphans | `true` |
| `fetch` | String | Fetch strategy | `LAZY`, `EAGER` |
| `optional` | Boolean | Optional relationship | `false` |
| `joinColumnName` | String | FK column name | `category_id` |
| `referencedColumnName` | String | Referenced column | `id` |
| `joinTableName` | String | Join table name | `post_tag` |
| `inverseJoinColumnName` | String | Inverse FK name | `tag_id` |

---

## ⚙️ Best Practices

### 1. **Always Use Validation**
```xml
<field>
    <name>email</name>
    <type>STRING</type>
    <required>true</required>
    <notBlank>true</notBlank>
    <email>true</email>
</field>
```

### 2. **Index Frequently Queried Fields**
```xml
<field>
    <name>username</name>
    <type>STRING</type>
    <unique>true</unique>
    <indexed>true</indexed>
</field>
```

### 3. **Use Cascade Wisely**
```xml
<!-- Good: Comments belong to post -->
<relationship>
    <relationshipType>ONE_TO_MANY</relationshipType>
    <relatedEntityName>Comment</relatedEntityName>
    <cascade>ALL</cascade>
    <orphanRemoval>true</orphanRemoval>
</relationship>

<!-- Bad: Don't cascade delete on category -->
<relationship>
    <relationshipType>MANY_TO_ONE</relationshipType>
    <relatedEntityName>Category</relatedEntityName>
    <cascade>PERSIST,MERGE</cascade>  <!-- NOT ALL -->
</relationship>
```

### 4. **Use LAZY Fetch for Collections**
```xml
<relationship>
    <relationshipType>ONE_TO_MANY</relationshipType>
    <relatedEntityName>Comment</relatedEntityName>
    <fetch>LAZY</fetch>  <!-- Prevents N+1 queries -->
</relationship>
```

### 5. **Specify mappedBy for Bidirectional Relationships**
```xml
<!-- Owning side -->
<relationship>
    <relationshipType>MANY_TO_ONE</relationshipType>
    <relatedEntityName>Post</relatedEntityName>
    <name>post</name>
</relationship>

<!-- Inverse side -->
<relationship>
    <relationshipType>ONE_TO_MANY</relationshipType>
    <relatedEntityName>Comment</relatedEntityName>
    <mappedBy>post</mappedBy>  <!-- Match field name above -->
</relationship>
```

---

## 🚀 Usage

1. **Create XML file** in `src/main/resources/generate-xmls/`
2. **Run code generator** (implementation dependent)
3. **Generated files** will be created in appropriate packages
4. **Compile** and verify generated code

---

## 📌 Notes

- All validation annotations are applied to Create and Update DTOs only
- Load DTOs do not have validation (used for reading)
- Unique constraints and indexes are created at database level
- Searchable fields are automatically included in search functionality
- Permissions and roles are auto-generated for each entity

---

## 🐛 Troubleshooting

### Issue: Field not validating
**Solution**: Ensure validation is in Create/Update DTOs, not Load DTOs

### Issue: Cascade delete not working
**Solution**: Check `cascade` and `orphanRemoval` settings

### Issue: Circular reference in JSON
**Solution**: Use `@JsonIgnore` or DTOs (already handled by generator)

### Issue: N+1 query problem
**Solution**: Use `fetch=LAZY` and @EntityGraph when needed

---

**Version**: 2.0.0
**Last Updated**: 2025-10-03
**Improvements**: Added validation, indexes, cascade options, fetch types, and column customization
