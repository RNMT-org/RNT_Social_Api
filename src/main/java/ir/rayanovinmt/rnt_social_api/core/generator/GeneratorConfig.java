package ir.rayanovinmt.rnt_social_api.core.generator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GeneratorConfig {
    private final String xmlFolderPath;
}