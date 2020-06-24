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
import ru.otus.springbatch.domain.jpa.CommentRdb;
import ru.otus.springbatch.domain.nosql.CommentMongo;
import ru.otus.springbatch.repository.nosql.CommentRepoMongo;
import ru.otus.springbatch.repository.rdb.CommentRepoRdb;
import ru.otus.springbatch.services.CommentService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CommentStepConfig {

    private static final int CHUNK_SIZE = 10;
    private final StepBuilderFactory stepBuilderFactory;
    private final CommentRepoRdb commentRepoRdb;
    private final CommentRepoMongo commentRepoMongo;
    private static final String READ_METHOD = "findAll";
    private static final String WRITE_METHOD = "save";

    @Bean
    public ItemProcessor<CommentRdb, CommentMongo> commentProcessor(CommentService commentService) {
        return commentService::transform;
    }

    @Bean
    public RepositoryItemReader<CommentRdb> commentReader(){
        RepositoryItemReader<CommentRdb> reader = new RepositoryItemReader<>();
        reader.setRepository(commentRepoRdb);
        reader.setMethodName(READ_METHOD);
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public RepositoryItemWriter<CommentMongo> commentWriter() {
        RepositoryItemWriter<CommentMongo> writer = new RepositoryItemWriter<>();
        writer.setRepository(commentRepoMongo);
        writer.setMethodName(WRITE_METHOD);
        return writer;
    }

    @Bean(name="commentsStep")
    public Step syncAuthorsStep(ItemProcessor commentProcessor,
                                RepositoryItemReader<CommentRdb> commentReader,
                                RepositoryItemWriter<CommentMongo> writer) {

        return stepBuilderFactory.get("syncAuthorsStep")
                .chunk(CHUNK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(writer)
                .build();
    }
}
