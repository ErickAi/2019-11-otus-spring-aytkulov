package ru.otus.studentstesting.service;

import java.util.List;
import java.util.Locale;

public interface LocalizationService {

    List<String> getAvailableLanguages();

    String getBundledMessage(String key);

    Locale getCurrentLocale();

    void setCurrentLocale(Locale locale);
}
