package ru.alexgur.firstapp.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FirstController {

    @GetMapping("/")
    public String get() {
        return """
                    Эта страница доступна всем.
                    Перейти в <a href="/manager">закрытый раздел</a>
                """;
    }

    @GetMapping("/manager")
    public Map<String, Object> getmanager(@AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> response = new HashMap<>();
        String userId = oidcUser.getSubject();

        response.put("userId", userId);
        response.put("fullName", oidcUser.getFullName());
        response.put("email", oidcUser.getAttribute("email"));
        response.put("roles", oidcUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return response;
    }
}
