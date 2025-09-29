package ir.rayanovinmt.rnt_social_api.core.async;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Profile("prod") // فقط در محیط dev فعال است
@Component
public class AsyncMetrics {

    private final AsyncThreadPoolTaskExecutor executor;

    public AsyncMetrics(AsyncThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Scheduled(fixedRate = 5000)
    public void logAsyncTaskStatus() {
        int activeCount = executor.getActiveCount();
        int poolSize = executor.getPoolSize();
        System.out.println("Async Thread Pool Status: Active Threads = " + activeCount + ", Total Pool Size = " + poolSize);
    }
}

