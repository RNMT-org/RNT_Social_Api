package ir.rayanovinmt.core.generator.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class BasePackage {

    String basePackage;
    String corePackage;

    public BasePackage(String basePackage) {
        this.basePackage = basePackage;
        this.corePackage = "ir.rayanovinmt.core";
    }

    public static String getDefaultBasePackage() {
        // Default base package for generated code
        return "ir.rayanovinmt.rnt_social_api";
    }

    public static String getDefaultCorePackage() {
        // Core package location (rnt-core module)
        return "ir.rayanovinmt.core";
    }
}
