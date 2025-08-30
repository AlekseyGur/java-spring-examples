package ru.alexgur.firstapp;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class KafkaApacheProducer {

    public static void start() {
        String baseProducerName = "Producer";
        Integer numberOfProducers = 1;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        ExecutorService executor = Executors.newFixedThreadPool(numberOfProducers);
        for (int num = 1; num <= numberOfProducers; num++) {
            String producerName = baseProducerName + "-" + num;
            executor.submit(() -> {
                System.out.println("Создан производитель с именем " + producerName);

                while (true) {
                    try {
                        Integer keyIdx = ThreadLocalRandom.current().nextInt(0, 10) + 1;
                        String messageKey = "key" + keyIdx;

                        Integer magicNumber = ThreadLocalRandom.current().nextInt(0, 100);
                        String messageValue = producerName + " " + magicNumber;

                        ProducerRecord<String, String> message =
                                new ProducerRecord<>("kafka-demo-1", null, System.currentTimeMillis(), messageKey, messageValue);
                        producer.send(message);

                        Integer timeToWait = ThreadLocalRandom.current().nextInt(0, 2000);
                        Thread.sleep(timeToWait);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}