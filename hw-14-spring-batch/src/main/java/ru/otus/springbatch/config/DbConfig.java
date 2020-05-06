package ru.otus.springbatch.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import ru.otus.springbatch.chandgelogs.InitMongoDBDataChangeLog;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DbConfig {

    private final AppProps appProps;
    private final DataSource dataSource;

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, appProps.getMongoDBName(),
                InitMongoDBDataChangeLog.class.getPackageName())
                .build();
    }

    @PostConstruct
    public void clearBatchTablesFromH2() {
        Resource resource = new ClassPathResource("clearBatchTablesFromH2.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }
}
