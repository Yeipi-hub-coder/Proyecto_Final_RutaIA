package com.rutaia.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // Cliente HTTP usado por N8nClientService para llamar el webhook de n8n
    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
