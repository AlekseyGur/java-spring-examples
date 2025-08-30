package ru.alexgur.firstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProducerApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApp.class, args);

//        KafkaProducerClient kafka = context.getBean(KafkaProducerClient.class);
//        kafka.start();
//        var kafka = context.getBean(KafkaProducerServiceImpl.class);
//        kafka.send("text ===================");

        KafkaApacheProducer.start();
    }
}