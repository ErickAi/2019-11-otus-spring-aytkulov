package ru.otus.booklibrary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BookLibraryAcl {

    public static void main(String[] args) {
        SpringApplication.run(BookLibraryAcl.class, args);
    }
}
