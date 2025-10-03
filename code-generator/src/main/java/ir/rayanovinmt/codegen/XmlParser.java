package ir.rayanovinmt.codegen;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ir.rayanovinmt.core.generator.model.EntityModel;
import ir.rayanovinmt.core.generator.model.RelationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    public static List<EntityModel> readEntitiesFromXml(String folderPath) throws IOException {
        List<EntityModel> entities = new ArrayList<>();
        logger.info("Reading entities from folder: {}", folderPath);

        try {
            File folder = new File(folderPath);

            if (!folder.exists()) {
                logger.warn("Folder does not exist: {}", folderPath);
                return entities;
            }

            if (!folder.isDirectory()) {
                throw new IOException("Path is not a directory: " + folderPath);
            }

            // List XML files in the folder
            File[] xmlFiles = folder.listFiles((dir, name) -> name.endsWith(".xml"));

            if (xmlFiles == null || xmlFiles.length == 0) {
                logger.warn("No XML files found in folder: {}", folderPath);
                return entities;
            }

            logger.info("Found {} XML files to process", xmlFiles.length);

            for (File xmlFile : xmlFiles) {
                try {
                    logger.debug("Processing XML file: {}", xmlFile.getName());
                    EntityModel entity = readEntityModelFromXml(xmlFile);
                    generateFromEntityModel(entity);
                    entities.add(entity);
                    logger.info("Successfully loaded entity: {}", entity.getName());
                } catch (IOException e) {
                    logger.error("Error processing file: " + xmlFile.getName(), e);
                    throw e;
                } catch (Exception e) {
                    logger.error("Error processing file: " + xmlFile.getName(), e);
                    throw new IOException("Failed to process XML file: " + xmlFile.getName(), e);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Folder not found: {}", folderPath, e);
            throw new IOException("Folder not found: " + folderPath, e);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error reading XML files from folder: {}", folderPath, e);
            throw new IOException("Error reading XML files from folder: " + folderPath, e);
        }

        return entities;
    }

    public static EntityModel readEntityModelFromXml(File xmlFile) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        XMLStreamReader xmlStreamReader = null;
        FileInputStream fileInputStream = null;

        try {
            logger.debug("Reading entity model from file: {}", xmlFile.getAbsolutePath());

            if (!xmlFile.exists()) {
                throw new FileNotFoundException("XML file not found: " + xmlFile.getAbsolutePath());
            }

            if (!xmlFile.canRead()) {
                throw new IOException("Cannot read XML file: " + xmlFile.getAbsolutePath());
            }

            // Obtain the FileInputStream and XMLStreamReader
            fileInputStream = new FileInputStream(xmlFile);
            xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);

            // Read and return the EntityModel
            EntityModel model = xmlMapper.readValue(xmlStreamReader, EntityModel.class);

            // Validate the model
            if (model.getName() == null || model.getName().isEmpty()) {
                throw new IllegalArgumentException("Entity name is required in XML file: " + xmlFile.getName());
            }

            if (model.getGenerate() == null) {
                logger.warn("Generate flag not specified for entity {}, defaulting to true", model.getName());
                model.setGenerate(true);
            }

            return model;
        } catch (XMLStreamException e) {
            logger.error("XML parsing error in file: {}", xmlFile.getName(), e);
            throw new IOException("Error reading EntityModel from XML file: " + xmlFile.getName(), e);
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", xmlFile.getName(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error reading XML file: {}", xmlFile.getName(), e);
            throw new IOException("Unexpected error reading EntityModel from XML file: " + xmlFile.getName(), e);
        } finally {
            // Close the XMLStreamReader if it was created
            if (xmlStreamReader != null) {
                try {
                    xmlStreamReader.close();
                } catch (XMLStreamException e) {
                    logger.warn("Error closing XMLStreamReader: {}", e.getMessage());
                }
            }
            // Close the FileInputStream if it was created
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.warn("Error closing FileInputStream: {}", e.getMessage());
                }
            }
        }
    }

    public static XMLStreamReader getXmlStreamReader(File xmlFile) throws FileNotFoundException, XMLStreamException {
        // Create an instance of XMLInputFactory
        XMLInputFactory factory = XMLInputFactory.newInstance();

        // Create FileInputStream from the given File
        FileInputStream fileInputStream = new FileInputStream(xmlFile);

        // Create and return the XMLStreamReader
        return factory.createXMLStreamReader(fileInputStream);
    }

    public static void generateFromEntityModel(EntityModel entityModel) {
        for (RelationModel relation : entityModel.getRelations()) {
            relation.setRelatedEntityPackage(RelationModel.generatePackageName(relation.getRelatedEntityName()));
        }
    }
}