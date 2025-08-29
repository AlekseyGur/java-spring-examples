package ru.alexgur.interactionapi.api;

import org.springframework.stereotype.Component;
import ru.alexgur.interactionapi.dto.ResponceDto;

@Component
public class SecondServiceClientFallback implements SecondServiceClient {

    @Override
    public ResponceDto getHello() {
        return new ResponceDto("second-service",
                "Второй сервис недоступен!", 503);
    }

    @Override
    public ResponceDto checkDelay(float delaySeconds) {
        return new ResponceDto("second-service",
                "Второй сервис недоступен по таймауту в " +
                        delaySeconds + " сек!", 503);
    }
}
