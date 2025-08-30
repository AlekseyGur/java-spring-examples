package ru.alexgur.firstapp;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.alexgur.grpc.user.UserRequest;
import ru.alexgur.grpc.user.UserResponse;
import ru.alexgur.grpc.user.UserServiceGrpc;

import java.time.Instant;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void sendUserRequest(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        int userId = request.getId();
        log.info("Запрос на получение данных пользователя с ID={}", userId);

        UserResponse response = buildUserResponse(userId);
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Отправлены данные пользователя с ID={}", userId);
    }

    private UserResponse buildUserResponse(int userId) {
        String name = "Alex";
        String email = "test@test.test";
        Instant createdOn = Instant.now();

        return UserResponse.newBuilder()
                .setId(userId)
                .setName(name)
                .setEmail(email)
                .setCreatedOn(com.google.protobuf.Timestamp.newBuilder()
                        .setSeconds(createdOn.getEpochSecond())
                        .setNanos(createdOn.getNano()))
                .build();
    }
}