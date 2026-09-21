package com.rutaia.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "n8n")
@Getter @Setter
public class N8nProperties {
    private String webhookUrl;
    private long timeoutMs;
}
