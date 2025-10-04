package ir.rayanovinmt.codegen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ir.rayanovinmt.core.generator.model.BasePackage;
import ir.rayanovinmt.core.generator.model.EntityModel;
import ir.rayanovinmt.core.generator.model.EnumModel;
import ir.rayanovinmt.core.generator.model.FieldModel;
import ir.rayanovinmt.core.generator.model.PermissionModel;
import ir.rayanovinmt.core.generator.model.RelationModel;
import ir.rayanovinmt.core.util.StringUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Step 2: Generate Java code from .cg (YAML) intermediate files
 *
 * Reads compiled .cg files and generates:
 * - Entity classes (JPA)
 * - Controllers (REST)
 * - Services
 * - Repositories
 * - DTOs (Create, Update, Load)
 * - Mappers (MapStruct)
 * - Permissions & Roles (JSON)
 */
public class CgToCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CgToCodeGenerator.class);
    private static final String TEMPLATE_DIR = "/templates";
    private static final String SRC_MAIN_JAVA = "src/main/java/";
    private static final String SRC_MAIN_RESOURCE = "src/main/resources/";

    /**
     * Main method for standalone execution
     */
    public static void main(String[] args) {
        try {
            String xmlFolderPath = args.length > 0 ? args[0] : "generate-xmls";
            logger.info("Using .cg files from: src/main/resources/{}/{}", xmlFolderPath, ".cg");

            generateFromCg(xmlFolderPath);

            logger.info("");
            logger.info("✅ Next steps:");
            logger.info("   1. Run: mvn clean compile");
            logger.info("   2. Run: mvn spring-boot:run");
            logger.info("   3. Test: http://localhost:8080/swagger-ui.html");
            logger.info("");

            System.exit(0);
        } catch (Exception e) {
            logger.error("Code generation failed", e);
            System.exit(1);
        }
    }

    private final Configuration freemarkerConfig;
    private final String basePackage;
    private final Validator validator;
    private final List<EntityModel> entities;
    private final GeneratorConfig config;

    public CgToCodeGenerator(GeneratorConfig config) throws IOException {
        this.config = config;
        this.freemarkerConfig = createFreemarkerConfig();
        this.basePackage = BasePackage.getDefaultBasePackage();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.entities = XmlToCgConverter.loadAllCgFiles(config.getXmlFolderPath());
        validateEntities(this.entities);
    }

    private Configuration createFreemarkerConfig() throws IOException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_33);
        config.setClassForTemplateLoading(this.getClass(), TEMPLATE_DIR);
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return config;
    }

    private void validateEntities(List<EntityModel> entities) {
        if ((entities == null || entities.isEmpty())) {
            throw new IllegalArgumentException("No entities found for generation");
        }

        entities.forEach(this::validateEntity);
    }

    private void validateEntity(EntityModel entity) {
        Set<ConstraintViolation<EntityModel>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            String errorMsg = violations.stream()
                    .map(v -> String.format("%s: %s", v.getPropertyPath(), v.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Invalid entity configuration: " + errorMsg);
        }
    }

    /**
     * Generate all code files from .cg files
     */
    public static void generateFromCg(String xmlFolderPath) throws CodeGenerationException {
        logger.info("╔════════════════════════════════════════════════════════════════╗");
        logger.info("║              STEP 2: .cg → Java Code Generation                ║");
        logger.info("╚════════════════════════════════════════════════════════════════╝");

        try {
            GeneratorConfig config = GeneratorConfig.builder()
                    .xmlFolderPath(xmlFolderPath)
                    .build();

            CgToCodeGenerator generator = new CgToCodeGenerator(config);
            generator.generateAll();

            logger.info("╔════════════════════════════════════════════════════════════════╗");
            logger.info("║  STEP 2 COMPLETE: Code generation successful                   ║");
            logger.info("╚════════════════════════════════════════════════════════════════╝");

        } catch (Exception e) {
            logger.error("Code generation failed", e);
            throw new CodeGenerationException("Code generation failed", e);
        }
    }

    public void generateAll() throws IOException, TemplateException {
        logger.info("Generating code for {} entities", entities.size());
        logger.info("");

        for (EntityModel entity : entities) {
            if (entity.getGenerate()) {
                logger.info("📦 Generating: {}", entity.getName());
                generateEntityCode(entity);
                logger.info("   ✅ Complete");
                logger.info("");
            }
        }
    }

    private void generateEntityCode(EntityModel entity) throws IOException, TemplateException {
        Map<String, Object> data = prepareTemplateData(entity);

        // Generate main files
        generateFile("EntityTemplate.ftl", entity.getName(), "Entity.java", data, null);
        generateFile("ControllerTemplate.ftl", entity.getName(), "Controller.java", data, null);
        generateFile("ServiceTemplate.ftl", entity.getName(), "Service.java", data, null);
        generateFile("RepositoryTemplate.ftl", entity.getName(), "Repository.java", data, null);

        // Generate DTOs
        generateDtos(entity, data);

        // Generate mapper
        generateFile("MapperTemplate.ftl", entity.getName(), "Mapper.java", data, null);

        // Generate enums if needed
        generateEnums(entity, data);

        // Generate constants
        generateFile("RoleTemplate.ftl", entity.getName(), "Role.java", data, "constant");
        generateFile("PermissionTemplate.ftl", entity.getName(), "Permission.java", data, "constant");

        // Generate security JSON files
        generatePermission(entity);
        generateRoles(entity);
    }

    private Map<String, Object> prepareTemplateData(EntityModel entity) {
        Map<String, Object> data = new HashMap<>();
        data.put("basePackage", basePackage);
        data.put("corePackage", BasePackage.getDefaultCorePackage());
        data.put("tableName", StringUtils.toSnakeCase(entity.getName()));
        data.put("entityName", entity.getName());
        data.put("fields", entity.getFields());
        data.put("relationships", entity.getRelations());
        data.put("searchableFields", getSearchableFields(entity));
        data.put("upperSnakeEntityName", StringUtils.toUpperSnakeCase(entity.getName()));
        return data;
    }

    private List<String> getSearchableFields(EntityModel entity) {
        List<String> searchableFields = new ArrayList<>();

        // Add entity fields
        entity.getFields().stream()
                .filter(FieldModel::isSearchable)
                .map(FieldModel::getName)
                .forEach(searchableFields::add);

        // Add relationship fields
        entity.getRelations().stream()
                .filter(RelationModel::isSearchable)
                .forEach(relation -> processSearchableRelation(relation, searchableFields));

        return searchableFields;
    }

    private void processSearchableRelation(RelationModel relation, List<String> searchableFields) {
        String relatedEntityName = relation.getRelatedEntityName();
        String relationshipName = relation.getRelationshipName();

        entities.stream()
                .filter(e -> e.getName().equals(relatedEntityName))
                .findFirst()
                .ifPresentOrElse(
                        relatedEntity -> relatedEntity.getFields().stream()
                                .filter(FieldModel::isSearchable)
                                .map(FieldModel::getName)
                                .map(field -> relationshipName + "." + field)
                                .forEach(searchableFields::add),
                        () -> {
                            throw new IllegalArgumentException("Cannot find related entity '" + relatedEntityName + "'");
                        }
                );
    }

    private void generateDtos(EntityModel entity, Map<String, Object> data) throws IOException, TemplateException {
        String[] dtoTypes = {"Load", "Update", "Create"};
        for (String type : dtoTypes) {
            data.put("dtoType", type);
            generateFile("DtoTemplate.ftl", entity.getName(), type + "Dto.java", data, "dto");
        }
    }

    private void generateEnums(EntityModel entity, Map<String, Object> data) throws IOException, TemplateException {
        if (!entity.hasFields()) return;

        for (FieldModel field : entity.getFields()) {
            List<EnumModel> enums = field.getEnums();
            if (enums != null && !enums.isEmpty()) {
                data.put("enumName", field.getName());
                data.put("enums", enums);
                generateFile("EnumTemplate.ftl", entity.getName(),
                        StringUtils.toUpperCamelCase(field.getName()) + "Enum.java", data, "constant");
            }
        }
    }

    private void generatePermission(EntityModel entity) throws IOException {
        String entityName = entity.getName().toLowerCase();
        List<PermissionModel> permissions = List.of(
                PermissionModel.builder().name(entityName + ":root").showName(entity.getName() + " Root").build(),
                PermissionModel.builder().name(entityName + ":create").showName(entity.getName() + " Create").build(),
                PermissionModel.builder().name(entityName + ":update").showName(entity.getName() + " Update").build(),
                PermissionModel.builder().name(entityName + ":read").showName(entity.getName() + " Read").build(),
                PermissionModel.builder().name(entityName + ":search").showName(entity.getName() + " Search").build(),
                PermissionModel.builder().name(entityName + ":delete").showName(entity.getName() + " Delete").build()
        );

        writeJsonToSecurityFile(entityName, "permissions", permissions);
    }

    private void generateRoles(EntityModel entity) throws IOException {
        String entityName = entity.getName().toLowerCase();
        Map<String, Object> role = new LinkedHashMap<>();
        role.put("name", StringUtils.toUpperSnakeCase(entityName) + "_ADMIN");
        role.put("permissions", List.of(
                entityName + ":root",
                entityName + ":create",
                entityName + ":read",
                entityName + ":search",
                entityName + ":update",
                entityName + ":delete"
        ));

        writeJsonToSecurityFile(entityName, "roles", List.of(role));
    }

    private void writeJsonToSecurityFile(String entityName, String fileType, Object data) throws IOException {
        Path dirPath = Paths.get(SRC_MAIN_RESOURCE + "security", entityName);
        Files.createDirectories(dirPath);
        Path filePath = dirPath.resolve(fileType + ".json");

        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            String json = new com.fasterxml.jackson.databind.ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(data);
            writer.write(json);
            logger.debug("   Generated {} file: {}", fileType, filePath);
        }
    }

    private void generateFile(String templateName, String entityName, String suffixName,
                              Map<String, Object> data, String subPackage) throws IOException, TemplateException {
        String outputPath = buildOutputPath(entityName, suffixName, subPackage);
        processTemplate(templateName, data, outputPath);
    }

    private String buildOutputPath(String entityName, String suffixName, String subPackage) {
        String packageDir = basePackage.replace(".", "/");
        String safeEntityName = entityName.toLowerCase();

        Path path = Paths.get(SRC_MAIN_JAVA, packageDir, safeEntityName);

        if (subPackage != null) {
            path = path.resolve(subPackage);
        }

        return path.resolve(entityName + suffixName).toString();
    }

    private void processTemplate(String templateName, Map<String, Object> data, String outputPath)
            throws IOException, TemplateException {
        ensureDirectoryExists(outputPath);

        try (FileWriter writer = new FileWriter(outputPath)) {
            Template template = freemarkerConfig.getTemplate(templateName);
            template.process(data, writer);
            logger.debug("   Generated: {}", outputPath);
        }
    }

    private void ensureDirectoryExists(String filePath) throws IOException {
        Path path = Paths.get(filePath).getParent();
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
