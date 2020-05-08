package ru.otus.booklibrarywebflux.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Book;
import ru.otus.booklibrarywebflux.repository.CommentRepo;

@Component
@RequiredArgsConstructor
public class MongoCommentCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepo commentRepo;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("id").toString();
        Mono mono = commentRepo.deleteAllByBookId(id);
    }
}