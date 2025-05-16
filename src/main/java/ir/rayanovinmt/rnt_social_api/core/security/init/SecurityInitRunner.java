package ir.rayanovinmt.rnt_social_api.core.security.init;

import ir.rayanovinmt.rnt_social_api.core.security.init.config.SecurityInitProperties;
import ir.rayanovinmt.rnt_social_api.core.security.init.service.loader.SecurityDataLoader;
import ir.rayanovinmt.rnt_social_api.core.security.init.service.persister.SecurityDataPersister;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityInitRunner implements ApplicationRunner {

    SecurityInitProperties properties;
    SecurityDataLoader dataLoader;
    SecurityDataPersister dataPersister;

    @Override
    public void run(ApplicationArguments args) {
        if (!properties.getEnabled()) {
            log.info("SecurityInit is disabled.");
            return;
        }

        log.info("Starting SecurityInit...");

        var permissions = dataLoader.loadPermissions();
        var roles = dataLoader.loadRoles();

        dataPersister.persistPermissions(permissions);
        dataPersister.persistRoles(roles);

        log.info("SecurityInit completed successfully.");
    }
}
