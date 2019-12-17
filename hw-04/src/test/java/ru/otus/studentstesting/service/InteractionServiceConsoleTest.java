package ru.otus.studentstesting.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.studentstesting.config.LocalizationProperties;
import ru.otus.studentstesting.domain.User;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static ru.otus.studentstesting.TestData.TEST_QUESTION;

@SpringBootTest(classes = {LocalizationProperties.class, InteractionServiceConsole.class})
@Profile("test")
@ContextConfiguration(classes = {LocalizationProperties.class, InteractionServiceConsole.class},
    initializers = ConfigFileApplicationContextInitializer.class)
@PropertySource(value = "classpath:application.yml")
@DisplayName(value = "Класс InteractionService")
class InteractionServiceConsoleTest {

    private static final String yes = "yes";
    private static final String condition = "condition";
    private static final String noMatter = "noMatter";
    private static User user = new User("Name", "Surname");

    @Autowired
    LocalizationProperties properties;
    @SpyBean
    private InteractionServiceConsole interactionService;
    @MockBean
    private InputOutputService ioService;
    @MockBean
    private LocalizationService localizationService;
    @MockBean
    private QuizService quizService;


    @Test
    @DisplayName(value = "Валидирует выбранный язык")
    void selectLanguage() {
        Mockito.when(ioService.getTyped()).thenReturn("ru");
        assertTrue(interactionService.selectLanguage());
    }

    @Test
    @DisplayName(value = "Запускает тестирование")
    void startQuiz() {
        Mockito.when(ioService.getTyped()).thenReturn(yes);
        Mockito.when(localizationService.getBundledMessage(yes)).thenReturn(yes);
        interactionService.startQuiz();
        Mockito.verify(interactionService, Mockito.times(1)).startQuizAgain(yes);
    }

    @Test
    @DisplayName(value = "Принимает ответы от пользователя")
    void getAnswer() {
        Mockito.when(localizationService.getBundledMessage(any())).thenReturn(condition);
        Mockito.when(ioService.getTyped()).thenReturn("yes");
        String answer = interactionService.getAnswer(TEST_QUESTION);

        assertTrue(TEST_QUESTION.getAnswer1().contains(answer));
    }

    @Test
    @DisplayName(value = "Принимает имя и фамилию и устанавливает пользователя")
    void login() {
        Mockito.when(ioService.getTyped()).thenReturn(user.getName()).thenReturn(user.getSurname());
        Mockito.when(localizationService.getBundledMessage(any())).thenReturn(noMatter);
        Mockito.doNothing().when(ioService).printBundledMessage(any());
        Mockito.when(quizService.isUserLoggedIn()).thenReturn(true);
        Mockito.when(quizService.getUser()).thenReturn(user);
        assertTrue(interactionService.login());
        Mockito.verify(interactionService, Mockito.times(1)).login(user.getName(), user.getSurname());
    }
}