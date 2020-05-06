package ru.otus.studentstesting;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.studentstesting.service.InteractionService;

@SpringBootApplication
@AllArgsConstructor
public class StudentsTestingApplication implements CommandLineRunner {

    private final InteractionService interactionService;

    public static void main(String[] args) {
        SpringApplication.run(StudentsTestingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        interactionService.startInteraction();
    }
}
