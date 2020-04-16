package ru.otus.booklibrarymongodb.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.booklibrarymongodb.changelog.InitMongoDBDataChangeLog;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    @Value("${spring.data.mongodb.database}")
    private String mongoDBName;

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoDBName,
                InitMongoDBDataChangeLog.class.getPackageName())
                .build();
    }
}
