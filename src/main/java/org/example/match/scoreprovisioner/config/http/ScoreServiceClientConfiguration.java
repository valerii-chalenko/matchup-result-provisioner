package org.example.match.scoreprovisioner.config.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ScoreServiceClientConfiguration {

    @Value("${integration.http.score-service.url}")
    private String url;

    @Bean
    RestClient scoreServiceRestClient() {

        return RestClient.builder().baseUrl(url).build();
    }
}
