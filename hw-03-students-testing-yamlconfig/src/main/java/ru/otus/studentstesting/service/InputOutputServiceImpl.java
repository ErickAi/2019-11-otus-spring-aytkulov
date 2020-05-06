package ru.otus.studentstesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class InputOutputServiceImpl implements InputOutputService {

    private final LocalizationService localizationService;

    private Scanner consoleScanner = new Scanner(System.in);

    @Override
    public String getTyped() {
        return consoleScanner.next();
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printSurroundQuotes(List<String> source) {
        StringBuilder builder = new StringBuilder();
        for (String s : source) {
            builder.append("'").append(s).append("' ");
        }
        System.out.println(builder.toString());
    }

    @Override
    public void printBundledMessage(String key) {
        print(localizationService.getBundledMessage(key));
    }
}
