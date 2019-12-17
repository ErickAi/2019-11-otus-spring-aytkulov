package ru.otus.studentstesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName(value = "Класс InputOutputService")
class InputOutputServiceImplTest {

    @MockBean
    LocalizationService localizationService;

    private InputOutputService inputOutputService;

    @BeforeEach
    void init() {
        inputOutputService = new InputOutputServiceImpl(localizationService);
    }

    @Test
    @DisplayName(value = "Корректно выводит строковые переменные")
    void print() {
        inputOutputService.print("\n\n\nMESSAGE\n\n\n");
    }

    @Test
    @DisplayName(value = "Корректно выводит BundledMessage")
    void printBundledMessage() {
        String testMessage = "Test message";
        Mockito.when(localizationService.getBundledMessage("testKey")).thenReturn(testMessage);
        inputOutputService.print("\n\n\n");
        inputOutputService.printBundledMessage("testKey");
        inputOutputService.print("\n\n\n");
    }
}