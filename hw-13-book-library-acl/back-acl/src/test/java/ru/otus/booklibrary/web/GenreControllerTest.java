package ru.otus.booklibrary.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.booklibrary.SecurityTestConfig;
import ru.otus.booklibrary.TestData;
import ru.otus.booklibrary.repo.GenreRepo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
@Import(SecurityTestConfig.class)
@DisplayName(value = "Контроллер жанров")
class GenreControllerTest {

    @MockBean
    private GenreRepo genreRepo;
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName(value = "для роли 'USER' запрещает доступ к жанрам (на уровне класса)")
    void getAllDenied() throws Exception {
        mvc.perform(get("/genres"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName(value = "для роли 'ADMIN' разрешает доступ к жанрам (на уровне класса)")
    void getAll() throws Exception {
        given(genreRepo.findAll()).willReturn(TestData.getAllGenres());
        mvc.perform(get("/genres"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }
}