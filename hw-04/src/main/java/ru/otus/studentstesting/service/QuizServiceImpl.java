package ru.otus.studentstesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.studentstesting.dao.QuestionDao;
import ru.otus.studentstesting.domain.Question;
import ru.otus.studentstesting.domain.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    protected final LocalizationService localizationService;
    private final QuestionDao questionDao;
    private User user;
    private int questionSize = 0;
    private int currentQuestionIndex = 0;
    private Question currentQuestion;
    private List<String> quizResults = new LinkedList<>();

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean isUserLoggedIn() {
        return user != null && !StringUtils.isEmpty(user.getName()) && !StringUtils.isEmpty(user.getSurname());
    }


    @Override
    public Map<Integer, Question> getQuestions() {
        return questionDao.getAll(localizationService.getCurrentLocale().getLanguage());
    }

    @Override
    public Question getCurrentQuestion() {
        currentQuestion = questionDao.getById(currentQuestionIndex, localizationService.getCurrentLocale().getLanguage());
        return currentQuestion;
    }

    @Override
    public Question getNextQuestion() {
        if (currentQuestionIndex == 0) {
            questionSize = questionDao.count();
        }
        currentQuestion = questionDao.getById(++currentQuestionIndex, localizationService.getCurrentLocale().getLanguage());
        return currentQuestion;
    }

    @Override
    public List<String> getQuizResults() {
        return quizResults;
    }

    @Override
    public void restart() {
        this.user = new User();
        this.questionSize = 0;
        this.currentQuestionIndex = 0;
        this.currentQuestion = null;
        this.quizResults = new LinkedList<>();
    }

    @Override
    public boolean isStarted() {
        return currentQuestion != null;
    }

    @Override
    public boolean isFinished() {
        return !quizResults.isEmpty() && questionSize == quizResults.size();
    }

    @Override
    public void addNextComment(Question question, String answer) {
        boolean isRight = question.getId() == 5 || question.getRightAnswer().toLowerCase().contains(answer.toLowerCase());
        String givenAnswer;
        if (question.getAnswer1().contains(answer) || answer.contains("1")) {
            givenAnswer = question.getAnswer1();
        } else if (question.getAnswer2().contains(answer) || answer.contains("2")) {
            givenAnswer = question.getAnswer2();
        } else if (question.getAnswer3().contains(answer) || answer.contains("3")) {
            givenAnswer = question.getAnswer3();
        } else givenAnswer = answer;

        quizResults.add(question.getId() + ". " + question.getQuestion() + rightOrWrongComment(isRight) + givenAnswer
            + (question.getId() != 5 ? rightAnswer() : "\n\t") + question.getRightAnswer());
    }

    private String rightOrWrongComment(boolean isRight) {
        return isRight ? localizationService.getBundledMessage("right") : localizationService.getBundledMessage("wrong");
    }

    private String rightAnswer() {
        return localizationService.getBundledMessage("rightAnswer");
    }
}