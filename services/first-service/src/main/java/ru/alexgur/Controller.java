package ru.alexgur;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Controller {
//    @Value("${server.port}")
//    private String port;

    @GetMapping
    public String hello() {
        return "Hello from service on port ";
    }
}