package ru.otus.studentstesting.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.studentstesting.config.LocalizationProperties;
import ru.otus.studentstesting.domain.Question;
import ru.otus.studentstesting.domain.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Profile("console")
public class InteractionServiceConsole implements InteractionService {
    private static final String SEPARATOR = "\n------------------------------------------------\n";

    private final InputOutputService ioService;
    private final LocalizationService localizationService;
    private final QuizService quizService;
    private String selectLanguageGreeting;
    private String selectOneOptionOf;
    private List<String> availableLanguages;
    private List<String> exitCommands;
    private Map<String, Locale> availableLocales;

    public InteractionServiceConsole(InputOutputService ioService, LocalizationService localizationService,
                                     QuizService quizService, LocalizationProperties properties) {
        this.ioService = ioService;
        this.localizationService = localizationService;
        this.quizService = quizService;
        selectLanguageGreeting = properties.getSelectLanguageGreeting();
        availableLanguages = properties.getAvailableLanguages();
        availableLocales = properties.getAvailableLocales();
        exitCommands = properties.getExitCommands();
        selectOneOptionOf = properties.getSelectOneOptionOf();
    }

    @Override
    public void startInteraction() {
        selectLanguage();
        instructions();
        login();
        startQuiz();
        showResults();
    }

    boolean selectLanguage() {
        ioService.print(SEPARATOR);
        ioService.print(selectLanguageGreeting);
        ioService.print(selectOneOptionOf);
        ioService.printSurroundQuotes(availableLanguages);
        ioService.printSurroundQuotes(exitCommands);
        return !selectLanguage(ioService.getTyped()).isEmpty();
    }

    @Override
    public String selectLanguage(String language) {

        if (availableLocales.containsKey(language)) {
            localizationService.setCurrentLocale(availableLocales.get(language));
            return availableLocales.get(language).toString();
        } else if (isExit(language)) {
            return "";
        }
        ioService.print(selectLanguageGreeting);
        ioService.print(selectOneOptionOf);
        ioService.printSurroundQuotes(availableLanguages);
        ioService.printSurroundQuotes(exitCommands);
        return selectLanguage(ioService.getTyped());
    }

    @Override
    public void instructions() {
        ioService.printBundledMessage("welcome");
        ioService.printBundledMessage("instruction.quiz");
        ioService.printBundledMessage("instruction.answer");
        ioService.printBundledMessage("instruction.exit");
        ioService.printBundledMessage("instruction.exit.commands");
        ioService.printSurroundQuotes(exitCommands);
    }

    boolean login() {
        ioService.print(SEPARATOR);
        ioService.printBundledMessage("enter-full-name");
        ioService.printBundledMessage("enter-your-name");
        String name = ioService.getTyped();
        isExit(name);
        ioService.printBundledMessage("enter-your-surname");
        String surname = ioService.getTyped();
        isExit(name);
        login(name, surname);
        return quizService.isUserLoggedIn();
    }

    @Override
    public String login(String name, String surname) {
        quizService.setUser(new User(name, surname));
        ioService.print(String.format(localizationService.getBundledMessage("authorized-as"),
            quizService.getUser().getName(), quizService.getUser().getSurname()));
        ioService.printBundledMessage("you-can-start");
        return "done";
    }

    @Override
    public void startQuiz() {
        ioService.printBundledMessage("start?");
        startQuizAgain(ioService.getTyped());
        List<Question> questions = new LinkedList<>(quizService.getQuestions().values());
        for (Question question : questions) {
            String answer = getAnswer(question);
            quizService.addNextComment(question, answer);
        }
    }

    @Override
    public void showResults() {
        ioService.print(SEPARATOR);
        ioService.printBundledMessage("results");
        for (String result : quizService.getQuizResults()) {
            ioService.print(result);
        }
        startNewTest();
    }

    String getAnswer(Question question) {
        ioService.print(localizationService.getBundledMessage("question-number") + question.getId());
        ioService.print(question.getQuestion());
        ioService.print(question.getAnswer1());
        ioService.print(question.getAnswer2());
        ioService.print(question.getAnswer3());
        return validateAnswer();
    }

    private String validateAnswer() {
        String answer = ioService.getTyped();
        if (exitCommands.contains(answer)) {
            ioService.printBundledMessage("you-are-abort");
            return null;
        } else if (!StringUtils.isNumeric(answer) && answer.length() < 3) {
            ioService.printBundledMessage("instruction.answer");
            return validateAnswer();
        }
        return answer;
    }

    @Override
    public void startNewTest() {
        ioService.print(SEPARATOR);
        ioService.print(SEPARATOR);
        ioService.printBundledMessage("start-test-again");
        ioService.printBundledMessage("instruction.exit.commands");
        ioService.printSurroundQuotes(exitCommands);
        if (startQuizAgain(ioService.getTyped())) {
            quizService.restart();
            startInteraction();
        }
    }

    public boolean isExit(String command) {
        if (exitCommands.contains(command)) {
            System.exit(0);
        }
        return false;
    }

    boolean startQuizAgain(String answer) {
        if (localizationService.getBundledMessage("yes").equals(answer) || "+".equals(answer)) {
            return true;
        } else if (isExit(answer)) {
            return false;
        }
        ioService.printBundledMessage("start?");
        ioService.printBundledMessage("instruction.exit.commands");
        ioService.printSurroundQuotes(exitCommands);
        return startQuizAgain(ioService.getTyped());
    }
}