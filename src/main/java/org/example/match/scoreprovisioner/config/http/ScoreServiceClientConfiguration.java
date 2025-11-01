package org.example.match.scoreprovisioner.config.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class ScoreServiceClientConfiguration {

    @Value("${integration.http.score-service.url}")
    private String url;

    @Bean
    RestClient scoreServiceRestClient() {
        SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
        rf.setConnectTimeout((int) Duration.ofSeconds(1).toMillis());
        rf.setReadTimeout((int) Duration.ofSeconds(2).toMillis());
        return RestClient.builder()
                .requestFactory(rf)
                .baseUrl(url)
                .build();
    }
}
