package org.example.match.scoreprovisioner.config.executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class SchedulerConfiguration {

    @Bean
    public ScheduledExecutorService scheduledExecutorService(
            @Value("${task.scheduler.pool-size:1}") int poolSize
    ) {
        return Executors.newScheduledThreadPool(poolSize);
    }
}
