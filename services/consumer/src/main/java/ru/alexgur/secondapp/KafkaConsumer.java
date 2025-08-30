package ru.alexgur.secondapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.alexgur.kafka.User.UserAvro;

import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}",
            containerFactory = "kafkaListenerContainerFactory") // фабрика контейнеров слушателей
    public void listen(ConsumerRecord<String, UserAvro> record) throws InterruptedException {

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