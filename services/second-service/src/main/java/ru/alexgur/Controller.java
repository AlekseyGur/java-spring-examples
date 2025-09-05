package ru.alexgur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/v1/second-service")
    public String get() {
        return "second-service!";
    }
}