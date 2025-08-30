package ru.alexgur.secondapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}",
            containerFactory = "kafkaListenerContainerFactory") // фабрика контейнеров слушателей
    public void listen(ConsumerRecord<String, String> record) throws InterruptedException {

        // многопоточность обработки настраивается в файле конфиг.
        // listener: concurrency: *цифра_кол-во_потоков*

        // Имитация задержки обработки
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));

        long deliveryTime = System.currentTimeMillis() - record.timestamp();
        log.info("Получено сообщение: значение={}, ключ={}, номер партиции={}, офсет={}, время доставки={} мс",
                record.value(),
                record.key(),
                record.partition(),
                record.offset(),
                deliveryTime);

    }
}