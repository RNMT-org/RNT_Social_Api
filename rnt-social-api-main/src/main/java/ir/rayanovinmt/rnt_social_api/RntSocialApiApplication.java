package ir.rayanovinmt.rnt_social_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {
    "ir.rayanovinmt.rnt_social_api",
    "ir.rayanovinmt.core"
})
@EnableJpaRepositories(basePackages = {
    "ir.rayanovinmt.rnt_social_api",
    "ir.rayanovinmt.core"
})
@EntityScan(basePackages = {
    "ir.rayanovinmt.rnt_social_api",
    "ir.rayanovinmt.core"
})
public class RntSocialApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RntSocialApiApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
