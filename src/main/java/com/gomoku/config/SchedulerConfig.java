package com.gomoku.config;

import static java.util.concurrent.Executors.newScheduledThreadPool;

import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting scheduler executor service.
 *
 * @author zeldan
 *
 */
@Configuration
public class SchedulerConfig {

    private static final int CORE_POOL_SIZE = 1;

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return newScheduledThreadPool(CORE_POOL_SIZE);
    }
}
