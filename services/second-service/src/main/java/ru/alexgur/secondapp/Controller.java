package ru.alexgur.secondapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexgur.interactionapi.api.SecondServiceClient;
import ru.alexgur.interactionapi.dto.ResponceDto;

import java.util.UUID;

@RestController
public class Controller implements SecondServiceClient {

    String uniqueInstanceID;

    public Controller() {
        this.uniqueInstanceID = UUID.randomUUID().toString();
    }

    @GetMapping("/")
    public ResponceDto getEcho() {
        return new ResponceDto("first-server", uniqueInstanceID, 200);
    }

    @Override
    public ResponceDto getHello() {
        String res = "Hello from second server! Server ID: " + uniqueInstanceID;
        return new ResponceDto("second-server",
                res,
                200);
    }

    @Override
    public ResponceDto checkDelay(float delaySeconds)  {
        try {
            Thread.sleep((long) (delaySeconds * 1000.0)); // Задержка в 60 секунд
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponceDto("second-server",
                "Hello",
                200);
    }

}