package ru.otus.booklibrary.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.booklibrary.SecurityTestConfig;
import ru.otus.booklibrary.TestData;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.services.BookService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.booklibrary.TestData.BOOK_MAGENTA;
import static ru.otus.booklibrary.TestData.BOOK_YELLOW;

@WebMvcTest(BookController.class)
@Import(SecurityTestConfig.class)
class BookControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mvc;

    @Test
    void getAll() throws Exception {
        given(bookService.getAll()).willReturn(TestData.getAll());
        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getById() throws Exception {
        given(bookService.getById(1L)).willReturn(TestData.BOOK_CYAN);
        mvc.perform(get("/books/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("cyan")));
    }

    @Test
    void getByName() throws Exception {
        given(bookService.getByName("magenta")).willReturn(BOOK_MAGENTA);
        mvc.perform(get("/books/name/magenta"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    void getByAuthorName() throws Exception {
        String authorNamePattern = "oth";
        given(bookService.getAllContainsAuthorName(authorNamePattern)).willReturn(Arrays.asList(BOOK_MAGENTA, BOOK_YELLOW));
        mvc.perform(get("/books/author/" + authorNamePattern))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void create() throws Exception {
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestData.NEW_BOOK)))
                .andExpect(status().isCreated());

        Mockito.verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    void update() throws Exception {
        Book modified = TestData.BOOK_YELLOW;
        modified.setName("modified name");
        mvc.perform(put("/books/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified)))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/books/1"))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).deleteById(any());
    }
}
