package ru.otus.booklibrarymongodb.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.booklibrarymongodb.changelog.InitMongoDBDataChangeLog;

@Configuration
@EnableMongoRepositories("ru.otus.booklibrarymongodb.repository")
@RequiredArgsConstructor
public class MongoConfig {

    private final AppProp appProp;

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, appProp.getMongoDBName(),
                InitMongoDBDataChangeLog.class.getPackageName())
                .build();
    }
}
