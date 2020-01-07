package ru.otus.studentstesting.service;

import ru.otus.studentstesting.domain.Question;

import java.util.List;

public interface InputOutputService {

    String getTyped();

    void print(String message);

    void printQuestion(Question question);

    void printSurroundQuotes(List<String> source);

    void printBundledMessage(String key);
}
