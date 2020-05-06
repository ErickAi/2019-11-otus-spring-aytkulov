package ru.otus.studentstesting.service;


import ru.otus.studentstesting.domain.Question;
import ru.otus.studentstesting.domain.User;

import java.util.List;
import java.util.Map;

interface QuizService {

    User getUser();

    void setUser(User user);

    boolean isUserLoggedIn();

    Map<Integer, Question> getQuestions();

    List<String> getQuizResults();

    void restart();

    void addNextComment(Question question, String answer);
}