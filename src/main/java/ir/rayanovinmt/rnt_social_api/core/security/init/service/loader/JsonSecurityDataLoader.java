package ir.rayanovinmt.rnt_social_api.core.security.init.service.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import ir.rayanovinmt.rnt_social_api.core.generator.model.PermissionModel;
import ir.rayanovinmt.rnt_social_api.core.generator.model.RoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class JsonSecurityDataLoader implements SecurityDataLoader {

    private static final Path SECURITY_BASE_PATH = Paths.get("src/main/resources/security");
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public Map<String, List<PermissionModel>> loadPermissions() {
        return load("permissions.json", PermissionModel.class);
    }

    @Override
    public Map<String, List<RoleModel>> loadRoles() {
        return load("roles.json", RoleModel.class);
    }

    private <T> Map<String, List<T>> load(String fileName, Class<T> clazz) {
        Map<String, List<T>> result = new HashMap<>();
        try (Stream<Path> entityDirs = Files.list(SECURITY_BASE_PATH)) {
            entityDirs.filter(Files::isDirectory).forEach(dir -> {
                Path jsonPath = dir.resolve(fileName);
                if (Files.exists(jsonPath)) {
                    try {
                        CollectionType listType = objectMapper.getTypeFactory()
                                .constructCollectionType(List.class, clazz);
                        List<T> items = objectMapper.readValue(jsonPath.toFile(), listType);
                        result.put(dir.getFileName().toString(), items);
                        log.info("Loaded {} from {}", fileName, jsonPath);
                    } catch (IOException e) {
                        log.warn("Failed to load {}: {}", jsonPath, e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            log.error("Failed to scan security directory: {}", e.getMessage());
        }
        return result;
    }
}