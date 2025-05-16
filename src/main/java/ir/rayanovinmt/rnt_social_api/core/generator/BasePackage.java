package ir.rayanovinmt.rnt_social_api.core.generator;

import ir.rayanovinmt.rnt_social_api.core.context.SpringContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class BasePackage {

    String basePackage;

    public BasePackage() {
        String fullPackageName = this.getClass().getPackage().getName();

        String[] packageSegments = fullPackageName.split("\\.");

        basePackage = String.join(".", Arrays.copyOfRange(packageSegments, 0, 3));
    }

    public static String childBasePackage() {
        return SpringContext.getProperty("generator.basePackage");
    }
}
