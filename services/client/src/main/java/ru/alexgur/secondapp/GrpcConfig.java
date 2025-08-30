package ru.alexgur.secondapp;


import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.alexgur.grpc.user.UserServiceGrpc;

@Configuration
public class GrpcConfig {

    @GrpcClient("demo-app")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Bean
    @Primary
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
        return userServiceBlockingStub;
    }
}
