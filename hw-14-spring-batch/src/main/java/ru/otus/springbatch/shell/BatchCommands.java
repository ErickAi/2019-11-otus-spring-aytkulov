package ru.otus.springbatch.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.springbatch.config.MongoProps;
import ru.otus.springbatch.domain.nosql.BookMongo;
import ru.otus.springbatch.domain.nosql.CommentMongo;
import ru.otus.springbatch.repository.nosql.BookRepoMongo;
import ru.otus.springbatch.repository.nosql.CommentRepoMongo;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final MongoProps mongoProps;

    private final CommentRepoMongo commentRepoMongo;
    private final BookRepoMongo bookRepoMongo;

    @ShellMethod(value = "printMigratedEntities", key = "p")
    public void repositoryTest() {
        for (BookMongo book : bookRepoMongo.findAll()) {
            System.out.println(book);
        }
        for (CommentMongo comment : commentRepoMongo.findAll()) {
            System.out.println(comment);
        }
    }

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobOperator() {
        JobExecution execution = jobLauncher.run(job, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters());
        System.out.println(execution);
        System.out.println("Mongo port: " + mongoProps.getPort());
    }
}
