package ru.otus.booklibrary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@Setter
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String uiClientId;
    private String uiClientSecret;
    private int accessTokenLifetime;
    private int refreshTokenLifetime;
    private String privateKeyPath;
    private String publicKeyPath;
}
