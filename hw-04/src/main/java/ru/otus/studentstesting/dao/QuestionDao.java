package ru.otus.studentstesting.dao;

import ru.otus.studentstesting.domain.Question;

import java.util.Map;

public interface QuestionDao {

    int count();

    Question getById(int id, String localization);

    Map<Integer, Question> getAll(String localization);
}