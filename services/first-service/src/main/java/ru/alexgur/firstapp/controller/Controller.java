package ru.alexgur.firstapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexgur.firstapp.dto.UserInfo;

@RestController
public class Controller {
    @GetMapping
    public String hello(@AuthenticationPrincipal UserInfo user) {
        if (user != null) {
            String res = "Hello, " + user.toString();

            if (user.hasRole("user")) {
                res += " Есть доступ уровня `User`";
            }

            if (user.hasRole("admin")) {
                res += " Есть доступ уровня `Admin`";
            }
            return res;
        }
        return "No user!";
    }

    // ошибка 403, если нет доступа:

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String user(@AuthenticationPrincipal UserInfo user) {

        return "Hello! " + user.toString();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(@AuthenticationPrincipal UserInfo user) {

        return "Hello! " + user.toString();
    }
}
