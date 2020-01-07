package ru.otus.studentstesting.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.studentstesting.config.LocalizationProperties;

import java.util.List;
import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService, InitializingBean {

    private final List<String> availableLanguages;
    private final LocalizationProperties properties;
    private final MessageSource messageSource;
    private Locale currentLocale;


    public LocalizationServiceImpl(MessageSource messageSource, LocalizationProperties properties) {
        this.availableLanguages = properties.getAvailableLanguages();
        this.messageSource = messageSource;
        this.properties = properties;
    }

    @Override
    public List<String> getAvailableLanguages() {
        return availableLanguages;
    }

    @Override
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    @Override
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }

    @Override
    public String getBundledMessage(String key) {
        return messageSource.getMessage(key, null, currentLocale);
    }

    @Override
    public void afterPropertiesSet() {
        currentLocale = properties.getAvailableLocales().entrySet().iterator().next().getValue();
    }
}
