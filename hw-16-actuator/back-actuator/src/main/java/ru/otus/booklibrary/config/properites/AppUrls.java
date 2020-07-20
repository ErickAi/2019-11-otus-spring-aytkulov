package ru.otus.booklibrary.config.properites;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@Setter
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.urls")
public class AppUrls {

    private String oauthToken;
    private String oauthCheckToken;
    private String oauthToken_key;
    private String userInfo;

}
