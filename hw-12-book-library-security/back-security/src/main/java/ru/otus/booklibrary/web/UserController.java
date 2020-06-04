package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.booklibrary.security.AuthUser;
import ru.otus.booklibrary.domain.User;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @GetMapping("/userinfo")
    public User userinfo(@AuthenticationPrincipal AuthUser authUser) {
        log.info("'/userinfo' for {}", authUser);
        return authUser.getUser();
    }
}
