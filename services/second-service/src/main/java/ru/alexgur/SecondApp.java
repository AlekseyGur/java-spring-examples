package ru.alexgur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SecondApp {

    public static void main(String[] args) {
        SpringApplication.run(SecondApp.class, args);
    }

}