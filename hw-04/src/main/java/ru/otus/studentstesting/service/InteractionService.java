package ru.otus.studentstesting.service;

public interface InteractionService {

    String SEPARATOR = "\n------------------------------------------------\n";

    void startInteraction();

    String selectLanguage(String language);

    void instructions();

    String login(String name, String surname);

    void startQuiz();

    void startNewTest();

    void showResults();

    boolean isExit(String command);
}
