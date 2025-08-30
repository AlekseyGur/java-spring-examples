package ru.alexgur.secondapp;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaApacheConsumer {

    public static void start() {
        String baseConsumerName = "Consumer";
        Integer numberOfConsumers = 1;

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "consumers-group-1");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfConsumers);
        for (int num = 1; num <= numberOfConsumers; num++) {
            String consumerName = baseConsumerName + "-" + num;
            executor.submit(() -> {
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                consumer.subscribe(Arrays.asList("kafka-demo-1"));

                System.out.println("Создан потребитель с именем " + consumerName);

                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        long timeToReceive = System.currentTimeMillis() - record.timestamp();
                        System.out.printf("Потребитель: %s, сообщение: %s, ключ: %s, номер партиции: %d, офсет: %d, время на доставку: %d%n",
                                consumerName, record.value(), record.key(), record.partition(), record.offset(), timeToReceive);
                    }
                }
            });
        }
    }
}