package ru.otus.booklibrary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BookLibraryDockerized {

    public static void main(String[] args) {
        SpringApplication.run(BookLibraryDockerized.class, args);
    }
}
