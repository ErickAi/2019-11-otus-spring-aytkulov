package ru.otus.studentstesting.service;

import java.util.List;

public interface InputOutputService {

    String getTyped();

    void print(String message);

    void printSurroundQuotes(List<String> source);

    void printBundledMessage(String key);
}
