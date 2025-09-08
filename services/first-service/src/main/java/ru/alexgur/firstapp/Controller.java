package ru.alexgur.firstapp;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexgur.interactionapi.api.SecondServiceClient;
import ru.alexgur.interactionapi.dto.RequestDto;
import ru.alexgur.interactionapi.dto.ResponceDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final Environment environment;
    private final SecondServiceClient secondServiceClient;

    @GetMapping("/")
    public ResponceDto getEcho(RequestDto req) {
        return new ResponceDto("first-server", req.getText(), 200);
    }

    @GetMapping("/hello")
    public ResponceDto checkSecondService() {
        ResponceDto hello = secondServiceClient.getHello();
        String res = "First server got msg from second server's instance: " + hello.getText();
        return new ResponceDto("first-server", res, hello.getStatusCode());
    }

    @GetMapping("/test")
    public ResponceDto getTest(RequestDto req) {
        Map<String, String> result = new HashMap<>();

        // Получаем переменные окружения
        result.put("Environment Variables:", "");
        System.getenv().forEach((key, value) -> result.put(key, value));

        return new ResponceDto("first-server",
                req.getText() + result.toString(),
                200);
    }
}
