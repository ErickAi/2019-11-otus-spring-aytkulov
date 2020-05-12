package ru.otus.booklibrarywebflux.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.TestData;
import ru.otus.booklibrarywebflux.domain.Book;
import ru.otus.booklibrarywebflux.repository.BookRepo;
import ru.otus.booklibrarywebflux.repository.CommentRepo;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static ru.otus.booklibrarywebflux.TestData.IDIOT;

@WebFluxTest(BookController.class)
@DisplayName("Контроллер книг")
class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @MockBean
    BookRepo bookRepo;
    @MockBean
    CommentRepo commentRepo;

    @Test
    @DisplayName("возвращает все книги")
    void getAll() {
        Flux<Book> books = Flux.fromIterable(TestData.getBooks());
        when(bookRepo.findAll()).thenReturn(books);

        webTestClient.get()
                .uri("/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(List::size, equalTo(3));
    }

    @Test
    @DisplayName("возвращает книгу по ее ID")
    void getById() {
        Mono<Book> book = Mono.just(IDIOT);
        when(bookRepo.findById(anyString())).thenReturn(book);

        webTestClient.get()
                .uri("/books/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(book1 -> book1, equalTo(IDIOT));
    }

    @Test
    @DisplayName("возвращает книгу по ее названию без учета регистра")
    void getByName() {
        Mono<Book> book = Mono.just(IDIOT);
        when(bookRepo.getByNameIgnoreCase(anyString())).thenReturn(book);

        webTestClient.get()
                .uri("/books/name/" + IDIOT.getName())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(book1 -> book1, equalTo(IDIOT));
    }

    @Test
    @DisplayName("возвращает созданную книгу")
    void create() {
        Mono<Book> idiotMono = Mono.just(IDIOT);
        when(bookRepo.insert(IDIOT)).thenReturn(idiotMono);

        webTestClient.post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(idiotMono), Book.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("обновляет книгу")
    void update() {
        Mono<Book> idiotMono = Mono.just(IDIOT);
        when(bookRepo.insert(IDIOT)).thenReturn(idiotMono);

        webTestClient.put()
                .uri("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(idiotMono), Book.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("удаляет книгу с ее комментариями")
    void delete() {
        when(bookRepo.deleteById(anyString())).thenReturn(Mono.empty());
        when(commentRepo.deleteAllByBookId(anyString())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/books/1")
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(commentRepo, times(1)).deleteAllByBookId(anyString());
    }
}