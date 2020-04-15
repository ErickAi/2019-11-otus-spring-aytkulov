package ru.otus.springbatch.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.springbatch.config.AppProps;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.springbatch.config.JobConfig.*;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final AppProps appProps;
    private final Job importAuthorJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() {
        JobExecution execution = jobLauncher.run(importAuthorJob,
                new JobParametersBuilder()
                        .addString(OUTPUT_FILE_NAME, appProps.getOutputFileName())
                        .toJobParameters());
        System.out.println(execution);
    }

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() {
        List<Long> executionIds = new ArrayList<>();
        executionIds.add(jobOperator.start(IMPORT_AUTHOR_JOB_NAME,
                OUTPUT_FILE_NAME + "=" + appProps.getOutputFileName()
        ));
        executionIds.add(jobOperator.start(IMPORT_GENRES_JOB_NAME,
                OUTPUT_FILE_NAME + "=" + appProps.getOutputFileName()
        ));
        executionIds.add(jobOperator.start(IMPORT_COMMENT_JOB_NAME,
                OUTPUT_FILE_NAME + "=" + appProps.getOutputFileName()
        ));
        for (Long l : executionIds) {
            System.out.println(jobOperator.getSummary(l));
        }
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_AUTHOR_JOB_NAME));
    }
}
