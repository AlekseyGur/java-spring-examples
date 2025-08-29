import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import ru.alexgur.firstapp.FirstApp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = FirstApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkSecondService() throws Exception {
        // Сначала отправляем короткий запрос, проверяем работоспособность
        mockMvc.perform(MockMvcRequestBuilders.get("/delay?delay=0.1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Проверяем, что вернулся статус 200
                .andDo(print());             // Печать результатов запроса

        // Теперь выполняем большое количество длинных запросов, которые переведут Circuit Breaker в состояние OPEN
        int threadCount = 15;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount); // Создание пула из 10 потоков
        CountDownLatch latch = new CountDownLatch(threadCount);                 // Синхронизатор для ожидания завершения всех потоков

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.get("/delay?delay=2")
                                    .accept(MediaType.APPLICATION_JSON))
                            .andDo(print());            // Логгирование каждого запроса
                } catch (Exception e) {
                    e.printStackTrace(); // Выводим стек-трейс в случае исключения
                } finally {
                    latch.countDown(); // Уменьшаем счётчик при завершении потока
                }
            });
        }

        // Ждём завершения всех потоков
        latch.await();

        // Закрываем пул потоков
        executor.shutdown();

        // Пробуем отправить запрос после перехода Circuit Breaker в открытое состояние
        mockMvc.perform(MockMvcRequestBuilders.get("/delay?delay=0.1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable()) // Проверяем, что возвращается статус 503 Service Unavailable
                .andDo(print());                           // Печать результатов запроса

        // Отправляем быстрый запрос, чтобы проверить автоматическое восстановление цепи
       for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.get("/delay?delay=0.1")
                                    .accept(MediaType.APPLICATION_JSON))
                            .andDo(print());            // Логгирование каждого запроса
                } catch (Exception e) {
                    e.printStackTrace(); // Выводим стек-трейс в случае исключения
                } finally {
                    latch.countDown(); // Уменьшаем счётчик при завершении потока
                }
            });
        }

        // Ждём завершения всех потоков
        latch.await();

        // Закрываем пул потоков
        executor.shutdown();

        // Теперь запрос должен точно выполниться
        mockMvc.perform(MockMvcRequestBuilders.get("/delay?delay=0.1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Возвращается успешный статус 200
                .andDo(print());             // Печать результатов запроса

    }

}
