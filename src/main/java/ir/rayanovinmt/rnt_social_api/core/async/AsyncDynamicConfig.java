package ir.rayanovinmt.rnt_social_api.core.async;

import org.springframework.stereotype.Component;

@Component
public class AsyncDynamicConfig {

    private final AsyncThreadPoolTaskExecutor executor;

    public AsyncDynamicConfig(AsyncThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public void updatePoolSize(int corePoolSize, int maxPoolSize) {
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        System.out.println("Updated Async Thread Pool: corePoolSize=" + corePoolSize + ", maxPoolSize=" + maxPoolSize);
    }
}
