package ru.otus.studentstesting.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import ru.otus.studentstesting.domain.Question;
import ru.otus.studentstesting.service.LocalizationService;
import ru.otus.studentstesting.config.LocalizationProperties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionDaoImpl implements QuestionDao, InitializingBean {

    private final Map<String, String> localizedQuestionResourceUrls;
    private Map<String, Map<Integer, Question>> localizedQuestionsMap;

    public QuestionDaoImpl(LocalizationProperties localizationProperties, LocalizationService localizationService) {
        this.localizedQuestionResourceUrls = localizationProperties.getQuestionsResourceUrls();
    }

    @Override
    public Question getById(int id, String localization) {
        Map<Integer, Question> questions = localizedQuestionsMap.get(localization);
        return questions.get(id);
    }

    @Override
    public int count() {
        return localizedQuestionsMap.entrySet().iterator().next().getValue().size();
    }

    @Override
    public Map<Integer, Question> getAll(String localization) {
        return localizedQuestionsMap.get(localization);
    }

    @Override
    public void afterPropertiesSet() {
        localizedQuestionsMap = new HashMap<>();
        for (Map.Entry<String, String> questionResourceUrls : localizedQuestionResourceUrls.entrySet()) {
            Map<Integer, Question> localizedQuestions = new HashMap<>();
            List<Question> questions = parseQuestions(questionResourceUrls.getValue());
            for (Question question : questions) {
                localizedQuestions.put(question.getId(), question);
            }
            localizedQuestionsMap.put(questionResourceUrls.getKey(), localizedQuestions);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Question> parseQuestions(String questionResourceUrl) {
        InputStream is = QuestionDao.class.getResourceAsStream(questionResourceUrl);

        CSVReader reader = new CSVReader(new InputStreamReader(is));
        HeaderColumnNameMappingStrategy<Question> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(Question.class);

        CsvToBean<Question> csvToBean = new CsvToBeanBuilder(reader)
            .withMappingStrategy(mappingStrategy)
            .withThrowExceptions(true)
            .build();
        try {
            return csvToBean.parse();
        } catch (RuntimeException e) {
            throw new RuntimeException("Недостаточно данных в файле с вопросами questions.csv", e);
        }
    }
}