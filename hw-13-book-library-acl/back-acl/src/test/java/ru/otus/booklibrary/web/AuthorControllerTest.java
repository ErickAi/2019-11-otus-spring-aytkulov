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
import ru.otus.booklibrary.repo.AuthorRepo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@Import(SecurityTestConfig.class)
@DisplayName(value = "Контроллер авторов")
class AuthorControllerTest {

    @MockBean
    private AuthorRepo authorRepo;
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName(value = "для роли 'USER' запрещает доступ к авторам (на уровне класса)")
    void getAllDenied() throws Exception {
        mvc.perform(get("/authors"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName(value = "для роли 'ADMIN' разрешает доступ к авторам (на уровне класса)")
    void getAll() throws Exception {
        given(authorRepo.findAll()).willReturn(TestData.getAllAuthors());
        mvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}