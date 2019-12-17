package ru.otus.studentstesting.service;

public interface InteractionService {

    void startInteraction();

    String selectLanguage(String language);

    void instructions();

    String login(String name, String surname);

    void startQuiz();

    void startNewTest();

    void showResults();

    boolean isExit(String command);
}
