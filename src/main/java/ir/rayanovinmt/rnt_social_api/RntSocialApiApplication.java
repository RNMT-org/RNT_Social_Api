package ir.rayanovinmt.rnt_social_api;

import ir.rayanovinmt.rnt_social_api.core.generator.CodeGenerator;
import ir.rayanovinmt.rnt_social_api.core.generator.GeneratorConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class RntSocialApiApplication {

    @Value("${generator.xmlFolderPath:generate-xmls}")
    private String xmlFolderPath;

    public static void main(String[] args) {
        SpringApplication.run(RntSocialApiApplication.class, args);
    }

    @Bean
    @ConditionalOnProperty(name = "generator.enabled", havingValue = "true")
    public CommandLineRunner startup() {
        return args -> {
            GeneratorConfig config = GeneratorConfig.builder()
                    .xmlFolderPath(xmlFolderPath)
                    .build();

            CodeGenerator.run(config);
        };
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
