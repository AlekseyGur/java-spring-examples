package ru.alexgur.interactionapi.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.alexgur.interactionapi.dto.ResponceDto;

@FeignClient(name = "second-service",
        fallback = SecondServiceClientFallback.class)
public interface SecondServiceClient {

    @GetMapping("/api/v1/hello")
    ResponceDto getHello();

    @GetMapping("/api/v1/delay")
    ResponceDto checkDelay(@RequestParam(value = "delay", defaultValue = "4.0") float delaySeconds);
}
