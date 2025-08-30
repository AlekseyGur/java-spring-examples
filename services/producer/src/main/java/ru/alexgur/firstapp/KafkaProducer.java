package ru.alexgur.firstapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.alexgur.avro.UserAvro;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, UserAvro> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String sendClientTopic;

    @Value("${spring.kafka.producer.number-of-producers}")
    private int numberOfProducers;

    @Value("${spring.kafka.producer.base-name}")
    private String baseProducerName;

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfProducers);
        for (int num = 1; num <= numberOfProducers; num++) {
            String producerName = baseProducerName + "-" + num;
            executor.submit(() -> {

                log.info("Создан производитель с именем " + producerName);

                while (true) {
                    try {
                        int keyIdx = ThreadLocalRandom.current().nextInt(0, 10) + 1;
                        String messageKey = "key" + keyIdx;

                        int magicNumber = ThreadLocalRandom.current().nextInt(0, 100);
                        UserAvro data = new UserAvro(
                                1,
                                "Alex",
                                true,
                                Instant.now()
                        );

                        ProducerRecord<String, UserAvro> message =
                                new ProducerRecord<>(sendClientTopic,
                                        null,
                                        System.currentTimeMillis(),
                                        messageKey,
                                        data);

                        kafkaTemplate.send(message);

                        log.info("Отправлено в kafka:" + message.toString());

                        int timeToWait = ThreadLocalRandom.current().nextInt(0, 2000);
                        Thread.sleep(timeToWait);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}