package ru.otus.acl.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.acl.EnableGlobalMethodSecurityForTests;
import ru.otus.acl.services.NoticeMessageService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.acl.TestData.EXISTS_ADMIN_MESSAGE;

@WebMvcTest(NoticeMessageController.class)
@Import(EnableGlobalMethodSecurityForTests.class)
@DisplayName("Security на уровне контроллера сообщений")
@ActiveProfiles("test")
class WebControllerSecurityTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    NoticeMessageService noticeMessageService;

    @Test
    @DisplayName("разрешает для 'USER' доступ к странице создания сообщения")
    @WithMockUser(/*default role 'USER'*/)
    void allowCreatePage() throws Exception {
        mvc.perform(get("/messages/add"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("разрешает для 'USER' доступ к странице обновления сообщения")
    @WithMockUser(/*default role 'USER'*/)
    void allowUpdatePage() throws Exception {
        Mockito.when(noticeMessageService.getById(1L)).thenReturn(EXISTS_ADMIN_MESSAGE);
        mvc.perform(get("/messages/1/edit"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("запрещает для 'SOMEONE' и 'GUEST' доступ к странице создания сообщения")
    @WithMockUser(roles = {"SOMEONE", "GUEST"})
    void denyCreatePage() throws Exception {
        mvc.perform(get("/messages/add"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("запрещает для 'SOMEONE' и 'GUEST' доступ к странице обновления сообщения")
    @WithMockUser(roles = {"SOMEONE", "GUEST"})
    void denyUpdatePage() throws Exception {
        Mockito.when(noticeMessageService.getById(1L)).thenReturn(EXISTS_ADMIN_MESSAGE);
        mvc.perform(get("/messages/1/edit"))
                .andExpect(status().isForbidden());
    }
}
