package ru.alexgur.firstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = {"ru.alexgur.interactionapi"}
)
@ComponentScan(
        basePackages = {
                "ru.alexgur.firstapp",
                "ru.alexgur.interactionapi"
        }
)
public class FirstApp {

    public static void main(String[] args) {
        SpringApplication.run(FirstApp.class, args);
    }

}