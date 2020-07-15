package ru.otus.acl.acl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.acl.domain.NoticeMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.acl.TestData.*;

@DisplayName("ACL конфигурация ПОЗВОЛЯЕТ пользователю с ролью")
class AclSecurityAllowTest extends AbstractAclSecurityIntegrationTest {

    @Test
    @DisplayName("'GUEST' просматривать все сообщения")
    @WithMockUser(roles = {"GUEST"})
    void getAll() {
        List<NoticeMessage> messages = service.getAll();
        assertTrue(messages.size() > 0);
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }

    @Test
    @DisplayName("'USER' просматривать все сообщения")
    @WithMockUser(/*default role 'USER'*/)
    void getById() {
        assertTrue(service.getAll().size() >= 3);
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }

    @Test
    @DisplayName("'USER' создвать новые сообщения")
    @WithMockUser(/*default value 'user'*/)
    void add() {
        service.add(NEW_USER_MESSAGE);
        assertTrue(repository.count() >= 4);
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }

    @Test
    @DisplayName("'USER' изменять свои сообщения")
    @WithMockUser(/*default value 'user'*/)
    void update() {
        String modifiedContent = "modified content";
        NoticeMessage modifiedMessage = new NoticeMessage(EXISTS_USER_MESSAGE_1);
        modifiedMessage.setContent(modifiedContent);
        service.update(modifiedMessage);
        NoticeMessage updatedMessage = repository.findById(EXISTS_USER_MESSAGE_1.getId()).orElseThrow(RuntimeException::new);
        assertEquals(modifiedMessage, updatedMessage);
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }

    @Test
    @DisplayName("'USER' удалять свои сообщения")
    @WithMockUser(/*default value 'user'*/)
    void delete() {
        service.delete(EXISTS_USER_MESSAGE_2);
        assertFalse(repository.findById(EXISTS_USER_MESSAGE_2.getId()).isPresent());
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }

    @Test
    @DisplayName("'ADMIN' изменять любые сообщения")
    @WithMockUser(roles = "ADMIN")
    void adminUpdate() {
        String modifiedContent = "modified content";
        NoticeMessage modifiedMessage = new NoticeMessage(EXISTS_USER_MESSAGE_1);
        modifiedMessage.setContent(modifiedContent);
        service.update(modifiedMessage);
        NoticeMessage updatedMessage = repository.findById(EXISTS_USER_MESSAGE_1.getId()).orElseThrow(RuntimeException::new);
        assertEquals(modifiedMessage, updatedMessage);
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }

    @Test
    @DisplayName("'ADMIN' удалять любые сообщения")
    @WithMockUser(roles = "ADMIN")
    void adminDelete() {
        service.delete(EXISTS_USER_MESSAGE_2);
        assertFalse(repository.findById(EXISTS_USER_MESSAGE_2.getId()).isPresent());
        System.out.println("\nmessageRepo.count(): " + repository.count());
    }
}
