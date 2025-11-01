package org.example.match.scoreprovisioner.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class EventScoreCache {

    @Bean
    public CacheManager cacheManager() {

        CaffeineCacheManager manager = new CaffeineCacheManager("scores");
        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(500));
        return manager;
    }

    @Bean(name = "scores")
    public org.springframework.cache.Cache scoresCache(CacheManager cacheManager) {
        // Ensure the named cache bean is available for direct injection
        org.springframework.cache.Cache cache = cacheManager.getCache("scores");
        if (cache == null) {
            throw new IllegalStateException("Caffeine cache 'scores' is not configured");
        }
        return cache;
    }
}
