package ru.otus.studentstesting;

import ru.otus.studentstesting.domain.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {

    public static final Question TEST_QUESTION = new Question(1, "Is this test question?"
        , "1 yes", "2 no", "3 maybe", "1 yes");
    public static final String RIGHT_ANSWER = "yes";
    public static final String WRONG_ANSWER = "no";

    public static Map<Integer, Question> getTestQuestionList() {
        Map<Integer, Question> questions = new HashMap<>();
        Question question = new Question(1, "Is this test question?"
            , "1 yes", "2 no", "3 maybe", "1 yes");
        for (int i = 1; i <= 5; i++) {
            question.setId(i);
            questions.put(i, question);
        }
        return questions;
    }

    public static List<String> getTestResultsList() {
        List<String> questions = new ArrayList<>();
        Question question = new Question(1, "Is this test question?"
            , "1 yes", "2 no", "3 maybe", "1 yes");
        for (int i = 1; i <= 5; i++) {
            questions.add(question.toString());
        }
        return questions;
    }
}
