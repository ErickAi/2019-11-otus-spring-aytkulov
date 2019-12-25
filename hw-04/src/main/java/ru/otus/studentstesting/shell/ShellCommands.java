package ru.otus.studentstesting.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.studentstesting.config.LocalizationProperties;
import ru.otus.studentstesting.domain.Question;
import ru.otus.studentstesting.service.InputOutputService;
import ru.otus.studentstesting.service.InteractionService;
import ru.otus.studentstesting.service.LocalizationService;
import ru.otus.studentstesting.service.QuizService;

import javax.annotation.PostConstruct;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final InteractionService interactionService;
    private final LocalizationService localizationService;
    private final InputOutputService ioService;
    private final QuizService quizService;
    private final LocalizationProperties properties;

    @PostConstruct
    public void init() {
    }

    @ShellMethod(value = "Select language command.", key = {"language", "lang"})
    public String selectLanguage(String language) {
        if (properties.getAvailableLocales().containsKey(language)) {
            return interactionService.selectLanguage(language);
        } else {
            ioService.printBundledMessage("language-not-exists");
            ioService.print(properties.getSelectLanguageGreeting());
            ioService.printSurroundQuotes(properties.getAvailableLanguages());
            return "";
        }
    }

    @ShellMethod(value = "Show current language command.", key = {"current-language", "c-lang"})
    public String currentLanguage() {
        return String.format(localizationService.getBundledMessage("selected-language")
                , localizationService.getCurrentLocale().getLanguage());
    }

    @ShellMethod(value = "Instructions for test.", key = {"instructions", "i"})
    public void instructions() {
        ioService.printBundledMessage("instruction.authorization");
        ioService.printBundledMessage("instruction.select-language");
        ioService.printSurroundQuotes(properties.getAvailableLanguages());
        ioService.printBundledMessage("instruction.or-use-default-lang");
        ioService.printBundledMessage("shell.use-commands");
        ioService.printBundledMessage("instruction.exit");
        ioService.printBundledMessage("instruction.exit.commands");
        ioService.printSurroundQuotes(properties.getExitCommands());
    }

    @ShellMethod(value = "Login command. required options: name, surname", key = {"login", "l"})
    public void login(@ShellOption("--name") String name, @ShellOption("--surname") String surname) {
        if (name != null && surname != null) {
            interactionService.login(name, surname);
        }
    }

    @ShellMethod(value = "Show current login command.", key = {"current-login", "c-login", "c-l"})
    public String currentLogin() {
        return String.format(localizationService.getBundledMessage("authorized-as"),
                quizService.getUser().getName(), quizService.getUser().getSurname());
    }

    @ShellMethod(value = "Start quiz.", key = {"start", "s"})
    public void startQuiz() {
        ioService.printBundledMessage("instruction.quiz");
        ioService.printBundledMessage("instruction.answer");
        ioService.printQuestion(quizService.getNextQuestion());
    }

    @ShellMethod(value = "Show current question command.", key = {"current-question", "current", "c"})
    public void currentQuestion() {
        ioService.printQuestion(quizService.getCurrentQuestion());
    }

    @ShellMethod(value = "Answer command.", key = {"answer", "a"})
    public void answer(String answer) {
        Question question = quizService.getCurrentQuestion();
        quizService.addNextComment(question, answer);
        if (quizService.isFinished()) {
            ioService.printBundledMessage("shell.unavailable.you-are-finished");
        } else {
            ioService.printQuestion(quizService.getNextQuestion());
        }
    }

    @ShellMethod(value = "Show quiz results.", key = {"results", "r"})
    public void showResults() {
        List<String> quizResults = quizService.getQuizResults();
        ioService.printBundledMessage("results");
        for (String result : quizResults) {
            ioService.print(result);
        }
    }

    @ShellMethod(value = "Restart command.", key = {"restart test", "r-t"})
    public void startNewTest() {
        quizService.restart();
    }

    private Availability loginAvailability() {
        if (quizService.isUserLoggedIn()) {
            return Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.already-authorized"));
        }
        return Availability.available();
    }


    private Availability handShakeAvailability() {
        if (!quizService.isUserLoggedIn()) {
            return Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.not-authorized"));
        }
        return Availability.available();
    }

    private Availability notStartedOrAlreadyFinished() {
        Availability availability = handShakeAvailability();
        if (quizService.isFinished()) {
            availability = Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.you-are-finished"));
        }
        return availability;
    }

    private Availability currentLoginAvailability() {
        return handShakeAvailability();
    }

    private Availability startQuizAvailability() {
        Availability availability = notStartedOrAlreadyFinished();
        if (quizService.isStarted()) {
            availability = Availability.unavailable(localizationService.getBundledMessage("shell.unavailable.need-restart"));
        }
        return availability;
    }

    private Availability currentQuestionAvailability() {
        Availability availability = notStartedOrAlreadyFinished();
        if (!quizService.isStarted()) {
            String reason = availability.getReason();
            availability = Availability.unavailable((reason == null ? "" : reason)
                    + localizationService.getBundledMessage("shell.unavailable.not-started"));
        }
        return availability;
    }

    private Availability answerAvailability() {
        return currentQuestionAvailability();
    }

    private Availability showResultsAvailability() {
        Availability inherit = currentLoginAvailability();
        if (!quizService.isFinished()) {
            String reason = inherit.getReason();
            return Availability.unavailable((reason == null ? "" : reason)
                    + localizationService.getBundledMessage("shell.unavailable.not-quizzed"));
        }
        return inherit;
    }
}
