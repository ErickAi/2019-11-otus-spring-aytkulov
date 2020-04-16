package ru.otus.springbatch.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.springbatch.domain.Author;
import ru.otus.springbatch.domain.Book;
import ru.otus.springbatch.domain.Comment;
import ru.otus.springbatch.domain.Genre;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Configuration
@AllArgsConstructor
public class JobConfig {
    private static final int SIMPLE_ITEMS_CHUNK_SIZE = 25;
    private static final int BOOK_CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String IMPORT_AUTHOR_JOB_NAME = "importAuthorJob";
    public static final String IMPORT_GENRES_JOB_NAME = "importGenreJob";
    public static final String IMPORT_COMMENT_JOB_NAME = "importCommentJob";
    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private final DataSource dataSource;
    private final MongoTemplate mongoTemplate;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private Map<String, JdbcCursorItemReader> tableReaders;
    private Map<String, String> tableQueries;

    @StepScope
    @Bean
    public CompositeItemWriter compositeItemWriter(@Value("#{jobParameters['" + OUTPUT_FILE_NAME + "']}") String outputFileName) {
        CompositeItemWriter compositeItemWriter = new CompositeItemWriter();
        compositeItemWriter.setDelegates(Arrays.asList(mongoWrigter(), flatFilewriter(outputFileName)));
        return compositeItemWriter;
    }

    public MongoItemWriter mongoWrigter() {
        return new MongoItemWriterBuilder<>()
                .template(mongoTemplate)
                .build();
    }

    public FlatFileItemWriter flatFilewriter(String outputFileName) {
        return new FlatFileItemWriterBuilder<>()
                .name("personItemWriter")
                .resource(new FileSystemResource(outputFileName))
                .lineAggregator(new DelimitedLineAggregator<>())
                .build();
    }

    //======= Authors migration =========
    @StepScope
    @Bean
    public JdbcCursorItemReader<Author> authorReader() {
        return new JdbcCursorItemReaderBuilder<Author>()
                .name("authorItemReader")
                .dataSource(dataSource)
                .beanRowMapper(Author.class)
                .sql("select * from authors")
                .build();
    }

    @Bean
    public Job importAuthorJob(Step stepAuthors) {
        return jobBuilderFactory.get(IMPORT_AUTHOR_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepAuthors)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step stepAuthors(@Qualifier("authorReader") ItemReader authorReader, CompositeItemWriter itemWriter) {
        return stepBuilderFactory.get("stepAuthors")        //@formatter:off
                .chunk(SIMPLE_ITEMS_CHUNK_SIZE)
                .reader(authorReader)
                .writer(itemWriter)
                .listener(new ItemReadListener() {
                    public void beforeRead() {logger.info("Начало чтения");}
                    public void afterRead(Object o) {logger.info("Конец чтения");}
                    public void onReadError(Exception e) {logger.info("Ошибка чтения");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {logger.info("Начало записи");}
                    public void afterWrite(List list) {logger.info("Конец записи");}
                    public void onWriteError(Exception e, List list) {logger.info("Ошибка записи");}
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }                                                       //@formatter:on


    //======= Genres migration =========
    @StepScope
    @Bean
    public JdbcCursorItemReader<Genre> genreReader() {
        return new JdbcCursorItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .dataSource(dataSource)
                .beanRowMapper(Genre.class)
                .sql("select * from genres")
                .build();
    }

    @Bean
    public Job importGenreJob(Step stepGenres) {
        return jobBuilderFactory.get(IMPORT_GENRES_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepGenres)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step stepGenres(@Qualifier("genreReader") ItemReader genreReader, CompositeItemWriter compositeWriter) {
        return stepBuilderFactory.get("stepGenres")        //@formatter:off
                .chunk(SIMPLE_ITEMS_CHUNK_SIZE)
                .reader(genreReader)
                .writer(compositeWriter)
                .listener(new ItemReadListener() {
                    public void beforeRead() {logger.info("Начало чтения");}
                    public void afterRead(Object o) {logger.info("Конец чтения");}
                    public void onReadError(Exception e) {logger.info("Ошибка чтения");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {logger.info("Начало записи");}
                    public void afterWrite(List list) {logger.info("Конец записи");}
                    public void onWriteError(Exception e, List list) {logger.info("Ошибка записи");}
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }                                                       //@formatter:on

    //======= Comments migration =========
    @StepScope
    @Bean
    public JdbcCursorItemReader<Comment> commentReader() {
        return new JdbcCursorItemReaderBuilder<Comment>()
                .name("commentItemReader")
                .dataSource(dataSource)
                .beanRowMapper(Comment.class)
                .sql("select * from comments")
                .build();
    }

    @Bean
    public Job importCommentJob(Step stepComments) {
        return jobBuilderFactory.get(IMPORT_COMMENT_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepComments)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step stepComments(@Qualifier("commentReader") ItemReader commentReader, CompositeItemWriter compositeWriter) {
        return stepBuilderFactory.get("stepComments")        //@formatter:off
                .chunk(SIMPLE_ITEMS_CHUNK_SIZE)
                .reader(commentReader)
                .writer(compositeWriter)
                .listener(new ItemReadListener() {
                    public void beforeRead() {logger.info("Начало чтения");}
                    public void afterRead(Object o) {logger.info("Конец чтения");}
                    public void onReadError(Exception e) {logger.info("Ошибка чтения");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {logger.info("Начало записи");}
                    public void afterWrite(List list) {logger.info("Конец записи");}
                    public void onWriteError(Exception e, List list) {logger.info("Ошибка записи");}
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }                                                       //@formatter:on

    //======= Book migration =========
    @StepScope
    @Bean
    public JdbcCursorItemReader<Book> bookReader() {
        return new JdbcCursorItemReaderBuilder<Book>()
                .name("bookItemReader")
                .dataSource(dataSource)
                .beanRowMapper(Book.class)
                .sql("select * from books")
                .build();
    }

    @Bean
    public Job importBookJob(Step stepBooks) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepBooks)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step stepBooks(@Qualifier("bookReader") ItemReader bookReader, CompositeItemWriter compositeWriter) {
        return stepBuilderFactory.get("stepBooks")        //@formatter:off
                .chunk(SIMPLE_ITEMS_CHUNK_SIZE)
                .reader(bookReader)
                .writer(compositeWriter)
                .listener(new ItemReadListener() {
                    public void beforeRead() {logger.info("Начало чтения");}
                    public void afterRead(Object o) {logger.info("Конец чтения");}
                    public void onReadError(Exception e) {logger.info("Ошибка чтения");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {logger.info("Начало записи");}
                    public void afterWrite(List list) {logger.info("Конец записи");}
                    public void onWriteError(Exception e, List list) {logger.info("Ошибка записи");}
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }                                                       //@formatter:on
}
