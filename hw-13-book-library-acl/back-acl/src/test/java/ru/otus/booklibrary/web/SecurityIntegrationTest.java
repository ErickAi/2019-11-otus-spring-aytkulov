package ru.otus.booklibrary.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.booklibrary.domain.Comment;
import ru.otus.booklibrary.exception.NotFoundException;
import ru.otus.booklibrary.repo.CommentRepo;

import javax.annotation.PostConstruct;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.booklibrary.TestData.ADMIN_COMMENT;
import static ru.otus.booklibrary.TestData.USER_COMMENT;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName(value = "Security приложения")
@ActiveProfiles({"integrationTest"})
class SecurityIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private MockMvc mvc;

    private String userToken;
    private String adminToken;

    private Comment userComment;
    private Comment adminComment;


    @PostConstruct
    void init() throws Exception {
        userToken = obtainAccessToken("u@u.u", "password");
        adminToken = obtainAccessToken("a@a.a", "password");
    }

    @Test
    @DisplayName(value = "отдает авторизованному пользователю комментарии для книги")
    void findByBookId() throws Exception {
        System.out.println("FIND BY BOOK ID");
        mvc.perform(get("/comments/book/2")
                .header("Authorization", "Bearer " + userToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName(value = "позволяет создателю комментария обновлять его")
    void update() throws Exception {
        Comment comment = new Comment(userComment);
        comment.setEntry("modified by User User");

        mvc.perform(put("/comments/" + comment.getId())
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk());

        Comment actual = commentRepo.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("something gone wrong"));
        assertEquals(comment, actual);
    }

    @Test
    @DisplayName(value = "запрещает пользователю изменять чужие комментарии")
    void updateOtherCommentDenied() throws Exception {
        Comment comment = new Comment(adminComment);
        comment.setEntry("modified by User User");

        mvc.perform(put("/comments/" + comment.getId())
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isForbidden());

        Comment actual = commentRepo.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("something gone wrong"));
        assertEquals(comment, actual);
    }

    @Test
    @DisplayName(value = "позволяет создателю комментария удалять его")
    void deleteUserComment() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/comments/" + userComment.getId())
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "запрещает пользователю удалять чужие комментарии")
    void deleteOtherCommentDenied() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/comments/" + userComment.getId())
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = "позволяет админу удалять любые комментарии")
    void deleteAdminComment() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/comments/" + adminComment.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.delete("/comments/" + userComment.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "отдает access_token для пользователя")
    void obtainAccessToken() {
        // @PostConstruct init() method
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "book-library-id");
        params.add("client_secret", "secret");
        params.add("username", username);
        params.add("password", password);
        System.out.println("GET ACCESS TOKEN");
        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("book-library-id", "secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    @DisplayName(value = "позволяет пользователям создавать комментарии")
    void createComment() {
        // @PostConstruct init() method
    }

  @BeforeEach
  void createComments() throws Exception {
        MvcResult mvcUserCommentResult = mvc.perform(post("/comments")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_COMMENT)))
                .andExpect(status().isCreated())
                .andReturn();
        userComment =  objectMapper.readValue(mvcUserCommentResult.getResponse().getContentAsString(), Comment.class);

        MvcResult mvcAdminCommentResult = mvc.perform(post("/comments")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ADMIN_COMMENT)))
                .andExpect(status().isCreated())
                .andReturn();
        adminComment = objectMapper.readValue(mvcAdminCommentResult.getResponse().getContentAsString(), Comment.class);
    }
}