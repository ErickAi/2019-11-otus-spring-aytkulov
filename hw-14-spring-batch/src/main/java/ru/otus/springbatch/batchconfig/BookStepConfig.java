package ru.otus.springbatch.batchconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import ru.otus.springbatch.domain.jpa.BookRdb;
import ru.otus.springbatch.domain.nosql.BookMongo;
import ru.otus.springbatch.repository.nosql.BookRepoMongo;
import ru.otus.springbatch.repository.rdb.BookRepoRdb;
import ru.otus.springbatch.services.BookService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BookStepConfig {

    private static final int CHUNK_SIZE = 10;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookRepoRdb bookRepoRdb;
    private final BookRepoMongo bookRepoMongo;
    private static final String READ_METHOD = "findAll";
    private static final String WRITE_METHOD = "save";

    @Bean
    public ItemProcessor<BookRdb, BookMongo> bookProcessor(BookService bookService) {
        return bookService::transform;
    }

    @Bean
    public RepositoryItemReader<BookRdb> bookReader(){
        RepositoryItemReader<BookRdb> reader = new RepositoryItemReader<>();
        reader.setRepository(bookRepoRdb);
        reader.setMethodName(READ_METHOD);
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public RepositoryItemWriter<BookMongo> bookWriter() {
        RepositoryItemWriter<BookMongo> writer = new RepositoryItemWriter<>();
        writer.setRepository(bookRepoMongo);
        writer.setMethodName(WRITE_METHOD);
        return writer;
    }

    @Bean(name="booksStep")
    public Step syncAuthorsStep(ItemProcessor bookProcessor,
                                RepositoryItemReader<BookRdb> bookReader,
                                RepositoryItemWriter<BookMongo> writer) {

        return stepBuilderFactory.get("syncAuthorsStep")
                .chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(writer)
                .build();
    }
}
