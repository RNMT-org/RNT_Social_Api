package ir.rayanovinmt.rnt_social_api.core.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ir.rayanovinmt.rnt_social_api.core.generator.model.EntityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Step 1: Convert XML entity definitions to .cg (YAML) intermediate files
 *
 * This allows developers to:
 * 1. Review the parsed entity structure before code generation
 * 2. Manually edit .cg files if needed
 * 3. Version control the intermediate representation
 * 4. Debug XML parsing issues
 */
public class XmlToCgConverter {

    private static final Logger logger = LoggerFactory.getLogger(XmlToCgConverter.class);
    private static final ObjectMapper yamlMapper;

    static {
        yamlMapper = new ObjectMapper(new YAMLFactory());
        // Ignore unknown properties when deserializing (like effectiveColumnName which is a method, not a field)
        yamlMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Main method for standalone execution
     */
    public static void main(String[] args) {
        try {
            String xmlFolderPath = args.length > 0 ? args[0] : "generate-xmls";
            logger.info("XML Folder: {}", xmlFolderPath);

            int count = convertXmlToCg(xmlFolderPath);

            if (count > 0) {
                System.exit(0);
            } else {
                logger.error("No entities converted");
                System.exit(1);
            }
        } catch (Exception e) {
            logger.error("Conversion failed", e);
            System.exit(1);
        }
    }

    /**
     * Convert all XML files in specified folder to .cg YAML files
     *
     * @param xmlFolderPath Folder containing XML entity definitions
     * @return Number of .cg files generated
     */
    public static int convertXmlToCg(String xmlFolderPath) throws IOException {
        logger.info("╔════════════════════════════════════════════════════════════════╗");
        logger.info("║              STEP 1: XML → .cg Conversion                      ║");
        logger.info("╚════════════════════════════════════════════════════════════════╝");

        // Load entities from XML
        logger.info("Loading XML entities from: {}", xmlFolderPath);
        List<EntityModel> entities = XmlParser.readEntitiesFromXml(xmlFolderPath);

        if (entities.isEmpty()) {
            logger.warn("No entities found in: {}", xmlFolderPath);
            return 0;
        }

        logger.info("Found {} entities to convert", entities.size());

        // Create .cg output directory
        Path cgDir = Paths.get("src/main/resources", xmlFolderPath, ".cg");
        Files.createDirectories(cgDir);
        logger.info("Output directory: {}", cgDir);

        // Convert each entity to .cg file
        int count = 0;
        for (EntityModel entity : entities) {
            if (entity.getGenerate()) {
                String cgFileName = entity.getName() + ".cg";
                Path cgFilePath = cgDir.resolve(cgFileName);

                writeCgFile(entity, cgFilePath);
                logger.info("  ✅ {} → {}", entity.getName() + ".xml", cgFileName);
                count++;
            } else {
                logger.info("  ⏭️  {} (generate=false, skipped)", entity.getName());
            }
        }

        logger.info("╔════════════════════════════════════════════════════════════════╗");
        logger.info("║  STEP 1 COMPLETE: {} .cg files generated", String.format("%-33s", count));
        logger.info("╚════════════════════════════════════════════════════════════════╝");
        logger.info("");
        logger.info("📁 Generated .cg files: {}", cgDir);
        logger.info("📝 Next: Review .cg files, then run code generation (Step 2)");
        logger.info("");

        return count;
    }

    /**
     * Write entity model to .cg YAML file
     */
    private static void writeCgFile(EntityModel entity, Path cgFilePath) throws IOException {
        File cgFile = cgFilePath.toFile();
        yamlMapper.writerWithDefaultPrettyPrinter().writeValue(cgFile, entity);
    }

    /**
     * Read entity model from .cg YAML file
     */
    public static EntityModel readCgFile(Path cgFilePath) throws IOException {
        return yamlMapper.readValue(cgFilePath.toFile(), EntityModel.class);
    }

    /**
     * Load all .cg files from directory
     */
    public static List<EntityModel> loadAllCgFiles(String xmlFolderPath) throws IOException {
        Path cgDir = Paths.get("src/main/resources", xmlFolderPath, ".cg");

        if (!Files.exists(cgDir)) {
            throw new IOException("No .cg directory found at: " + cgDir +
                                "\nRun XML → .cg conversion first (Step 1)");
        }

        logger.info("Loading .cg files from: {}", cgDir);

        List<EntityModel> entities = Files.list(cgDir)
                .filter(path -> path.toString().endsWith(".cg"))
                .map(path -> {
                    try {
                        EntityModel entity = readCgFile(path);
                        logger.debug("  Loaded: {}", path.getFileName());
                        return entity;
                    } catch (IOException e) {
                        logger.error("Failed to read .cg file: {}", path, e);
                        throw new RuntimeException("Failed to read .cg file: " + path, e);
                    }
                })
                .toList();

        logger.info("Loaded {} entities from .cg files", entities.size());
        return entities;
    }
}
