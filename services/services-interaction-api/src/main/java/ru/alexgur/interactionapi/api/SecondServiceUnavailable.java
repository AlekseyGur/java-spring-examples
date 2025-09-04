package ru.alexgur.interactionapi.api;

public class SecondServiceUnavailable extends RuntimeException {
    public SecondServiceUnavailable(String message) {
        super(message);
    }
}
