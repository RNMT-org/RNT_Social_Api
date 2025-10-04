package ir.rayanovinmt.core.security.init;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.init")
@Getter
@Setter
public class SecurityInitConfig {
    private boolean enabled = true;
    private SuperAdminConfig superAdmin = new SuperAdminConfig();

    @Getter
    @Setter
    public static class SuperAdminConfig {
        private String username = "admin";
        private String password = "Admin@12345";
        private String email = "admin@rnt.ir";
        private String name = "System Administrator";
    }
}
