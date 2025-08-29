package ru.alexgur.sondapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.alexgur.interactionapi.api.SecondServiceClient;
import ru.alexgur.interactionapi.dto.ResponceDto;

@RestController
@RequiredArgsConstructor
public class Controller implements SecondServiceClient {

    @Override
    public ResponceDto getHello() {
        String res = "Hello from second server! PID: " + ProcessHandle.current().pid();
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