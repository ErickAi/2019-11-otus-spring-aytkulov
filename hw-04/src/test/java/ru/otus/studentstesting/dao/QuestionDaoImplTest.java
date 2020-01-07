package ru.otus.studentstesting.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.studentstesting.config.LocalizationProperties;
import ru.otus.studentstesting.domain.Question;
import ru.otus.studentstesting.service.LocalizationService;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LocalizationProperties.class, QuestionDaoImpl.class})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName(value = "Класс QuestionDao инициализируется LocalizationProperties и")
class QuestionDaoImplTest {

    @Autowired
    LocalizationProperties properties;
    @Autowired
    QuestionDaoImpl questionDao;
    @MockBean
    LocalizationService localizationService;

    @Test
    @DisplayName(value = "Загружает вопросы в русской локализации")
    void getAllRu() {
        Mockito.when(localizationService.getCurrentLocale()).thenReturn(new Locale("ru", "RU"));
        Map<Integer, Question> questions = questionDao.getAll("ru");
        assertNotNull(questions);
    }

    @Test
    @DisplayName(value = "Загружает вопросы в английской локализации")
    void getAllEn() {
        Mockito.when(localizationService.getCurrentLocale()).thenReturn(new Locale("en", "EN"));
        Map<Integer, Question> questions = questionDao.getAll("en");
        assertFalse(questions.isEmpty());
    }

    @Test
    @DisplayName(value = "Возвращает вопрос по его идентификатору")
    void getOneEn() {
        Question question = questionDao.getById(3, "en");
        assertEquals(3, question.getId());
    }
}