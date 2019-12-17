package ru.otus.studentstesting.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.localization-properties")
public class LocalizationProperties {

    public static final String RUSSIAN_RU = "ру";
    public static final String ENGLISH_RU = "ru";
    private String selectLanguageGreeting;
    private String selectOneOptionOf;
    private List<String> availableLanguages;
    private List<String> exitCommands;
    private HashMap<String, String> questionsResourceUrls;

    public Map<String, Locale> getAvailableLocales() {
        Map<String, Locale> localesMap = new HashMap<>();
        for (String command : availableLanguages) {
            if (RUSSIAN_RU.equals(command)) {
                localesMap.put(command, new Locale(ENGLISH_RU, ENGLISH_RU.toUpperCase()));
            } else {
                localesMap.put(command, new Locale(command, command.toUpperCase()));
            }
        }
        return localesMap;
    }
}