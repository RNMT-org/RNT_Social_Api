package ir.rayanovinmt.rnt_social_api.core.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standalone Code Generator Application
 *
 * This is a command-line tool to generate CRUD code from XML entity definitions.
 * It runs separately from the main Spring Boot application.
 *
 * Usage:
 *   mvn exec:java -Dexec.mainClass="ir.rayanovinmt.rnt_social_api.core.generator.GeneratorApplication"
 *
 * Or create a shell script to run it easily.
 */
public class GeneratorApplication {

    private static final Logger logger = LoggerFactory.getLogger(GeneratorApplication.class);

    public static void main(String[] args) {
        logger.info("=".repeat(80));
        logger.info("RNT Code Generator - Standalone Tool");
        logger.info("=".repeat(80));

        try {
            // Parse command line arguments
            GeneratorConfig config = parseArguments(args);

            // Run the generator
            logger.info("Starting code generation...");
            CodeGenerator.run(config);

            logger.info("=".repeat(80));
            logger.info("Code generation completed successfully!");
            logger.info("=".repeat(80));
            logger.info("Generated files location: src/main/java/{basePackage}/{{entityName}}/");
            logger.info("Next steps:");
            logger.info("  1. Review the generated code");
            logger.info("  2. Run 'mvn compile' to compile");
            logger.info("  3. Run 'mvn spring-boot:run' to start the application");
            logger.info("=".repeat(80));

            System.exit(0);

        } catch (CodeGenerationException e) {
            logger.error("=".repeat(80));
            logger.error("CODE GENERATION FAILED!");
            logger.error("=".repeat(80));
            logger.error("Error: {}", e.getMessage(), e);
            logger.error("=".repeat(80));
            System.exit(1);
        } catch (Exception e) {
            logger.error("=".repeat(80));
            logger.error("UNEXPECTED ERROR!");
            logger.error("=".repeat(80));
            logger.error("Error: {}", e.getMessage(), e);
            logger.error("=".repeat(80));
            System.exit(1);
        }
    }

    private static GeneratorConfig parseArguments(String[] args) {
        String xmlFolderPath = "generate-xmls";

        // Check for custom XML folder path
        if (args.length > 0) {
            xmlFolderPath = args[0];
            logger.info("Using custom XML folder: {}", xmlFolderPath);
        } else {
            logger.info("Using default XML folder: {}", xmlFolderPath);
        }

        return GeneratorConfig.builder()
                .xmlFolderPath(xmlFolderPath)
                .build();
    }
}
