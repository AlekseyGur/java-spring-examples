package ru.alexgur.firstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProducerApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApp.class, args);
        KafkaProducer kafka = context.getBean(KafkaProducer.class);
        kafka.start();
    }
}