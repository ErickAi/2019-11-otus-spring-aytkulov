package ru.otus.studentstesting.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import ru.otus.studentstesting.config.LocalizationProperties;
import ru.otus.studentstesting.dao.QuestionDao;
import ru.otus.studentstesting.domain.User;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.studentstesting.TestData.*;

@SpringBootTest(classes = {QuizServiceImpl.class})
@Profile("test")
@DisplayName(value = "Класс QuizService")
class QuizServiceImplTest {

    private static final String right = "right";
    private static final String wrong = "wrong";
    private static final String rightAnswer = "rightAnswer";

    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private LocalizationService localizationService;
    @MockBean
    private LocalizationProperties localizationProperties;
    @MockBean
    private InputOutputService ioService;

    @SpyBean
    private QuizServiceImpl quizService;

    @Test
    @DisplayName(value = "Корркетно комментирует правильные ответы")
    void prepareSingleCommentRight() {
        Mockito.when(localizationService.getBundledMessage(right)).thenReturn(right);
        Mockito.when(localizationService.getBundledMessage(rightAnswer)).thenReturn(rightAnswer);
        quizService.addNextComment(TEST_QUESTION, RIGHT_ANSWER);
        String comment = quizService.getQuizResults().get(0);
        assertTrue(comment.contains(right));
    }

    @Test
    @DisplayName(value = "Корркетно комментирует неправильные ответы")
    void prepareSingleCommentWrong() {
        Mockito.when(localizationService.getBundledMessage(wrong)).thenReturn(wrong);
        Mockito.when(localizationService.getBundledMessage(rightAnswer)).thenReturn(rightAnswer);
        quizService.addNextComment(TEST_QUESTION, WRONG_ANSWER);
        String comment = quizService.getQuizResults().get(1);
        assertTrue(comment.contains(wrong));
    }

    @Test
    @DisplayName(value = "Устанавливает текущего пользователя")
    void isUserLoggedIn() {
        quizService.setUser(new User("name", "surname"));
        assertTrue(quizService.isUserLoggedIn());
    }
}
