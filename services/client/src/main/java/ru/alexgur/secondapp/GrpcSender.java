package ru.alexgur.secondapp;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexgur.grpc.user.UserRequest;
import ru.alexgur.grpc.user.UserResponse;
import ru.alexgur.grpc.user.UserServiceGrpc;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Service
public class GrpcSender {
    private final UserServiceGrpc.UserServiceBlockingStub grpcClient;

    @Autowired
    public GrpcSender(UserServiceGrpc.UserServiceBlockingStub grpcClient) {
        this.grpcClient = grpcClient;
    }

    public void sendAction() {
        UserRequest req = UserRequest.newBuilder().setId(1).build();

        try {
            log.info("Отправлено в grpc {}", req);
            UserResponse res = grpcClient.sendUserRequest(req);
            log.info("Получено от grpc {}", res);
            log.info("Дата {}", formatTimestamp(res.getCreatedOn()));
        } catch (StatusRuntimeException ex) {
            log.error("Ошибка при отправке grpc-запроса: ", ex);
        }
    }

    private String formatTimestamp(com.google.protobuf.Timestamp timestamp) {
        long epochSecond = timestamp.getSeconds();
        int nanoOfSecond = timestamp.getNanos();

        Instant instant = Instant.ofEpochSecond(epochSecond, nanoOfSecond);
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());

        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withLocale(Locale.getDefault()) // Локализация формата
                .format(zdt);
    }
}
