package ru.otus.studentstesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.studentstesting.domain.Question;

import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class InputOutputServiceImpl implements InputOutputService {

    private final LocalizationService localizationService;

    private Scanner consoleScanner = new Scanner(System.in);

    public String getTyped() {
        return consoleScanner.next();
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void printQuestion(Question question) {
        print(localizationService.getBundledMessage("question-number") + question.getId());
        print(question.getQuestion());
        print(question.getAnswer1());
        print(question.getAnswer2());
        print(question.getAnswer3());
        print(localizationService.getBundledMessage("shell.use-answer-command"));
    }

    public void printSurroundQuotes(List<String> source) {
        StringBuilder builder = new StringBuilder();
        for (String s : source) {
            builder.append("'").append(s).append("' ");
        }
        System.out.println(builder.toString());
    }

    public void printBundledMessage(String key) {
        print(localizationService.getBundledMessage(key));
    }
}
