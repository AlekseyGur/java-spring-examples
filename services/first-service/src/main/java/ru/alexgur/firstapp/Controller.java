package ru.alexgur.firstapp;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import ru.alexgur.interactionapi.api.SecondServiceClient;
import ru.alexgur.interactionapi.dto.RequestDto;
import ru.alexgur.interactionapi.dto.ResponceDto;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final SecondServiceClient secondServiceClient;

    @GetMapping("/echo")
    public ResponceDto getEcho(RequestDto req) {
        return new ResponceDto("first-server", req.getText(), 200);
    }

    @GetMapping("/hello")
    public ResponceDto checkSecondService() {
        ResponceDto hello = secondServiceClient.getHello();
        String res = "First server got msg from second server's instance: " + hello.getText();
        return new ResponceDto("first-server", res, hello.getStatusCode());
    }

    @GetMapping("/delay")
    public ResponceDto checkSecondServiceDelay(@RequestParam(value = "delay", defaultValue = "4.0") float delaySeconds) {
        ResponceDto hello = secondServiceClient.checkDelay(delaySeconds);
        String res = "First server got msg from second server's instance: " + hello.getText();
        return new ResponceDto("first-server", res, hello.getStatusCode());
    }
}