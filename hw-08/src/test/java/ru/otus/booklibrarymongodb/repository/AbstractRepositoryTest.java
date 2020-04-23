package ru.otus.booklibrarymongodb.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.booklibrarymongodb.config", "ru.otus.booklibrarymongodb.repository"})
abstract class AbstractRepositoryTest {
}
