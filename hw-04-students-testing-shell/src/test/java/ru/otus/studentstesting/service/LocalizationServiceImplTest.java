package ru.otus.studentstesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.studentstesting.config.LocalizationProperties;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {LocalizationProperties.class})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName(value = "Класс LocalizationService")
class LocalizationServiceImplTest {

    @Autowired
    LocalizationProperties properties;
    private LocalizationService localizationService;

    @Bean
    private MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @BeforeEach
    void init() {
        localizationService = new LocalizationServiceImpl(messageSource(), properties);
    }

    @Test
    @DisplayName(value = "Возвращает доступные локализации")
    void getAvailableLanguages() {
        List<String> languages = localizationService.getAvailableLanguages();
        assertAll("которые содержат русский и английский языки",
            () -> assertThat(languages.contains("ru")),
            () -> assertThat(languages.contains("en"))
        );

    }

    @Test
    @DisplayName(value = "Загружает ресурсы сообщений на английском языке")
    void getBundledMessageEn() {
        localizationService.setCurrentLocale(new Locale("en", "EN"));
        assertThat(localizationService.getBundledMessage("yes").equals("yes"));
    }

    @Test
    @DisplayName(value = "Загружает ресурсы сообщений на русском языке")
    void getBundledMessageRu() {
        localizationService.setCurrentLocale(new Locale("ru", "RU"));
        assertThat(localizationService.getBundledMessage("yes").equals("да"));
    }
}