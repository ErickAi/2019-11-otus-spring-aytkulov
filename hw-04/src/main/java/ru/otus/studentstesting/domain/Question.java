package ru.otus.studentstesting.domain;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(of = {"id", "question", "rightAnswer"})
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "question")
    private String question;
    @CsvBindByName(column = "answer1")
    private String answer1;
    @CsvBindByName(column = "answer2")
    private String answer2;
    @CsvBindByName(column = "answer3")
    private String answer3;
    @CsvBindByName(column = "rightAnswer")
    private String rightAnswer;
}