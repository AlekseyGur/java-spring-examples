package ru.alexgur.kafka.deserializer;

import ru.alexgur.avro.UserAvro;

public class UserDeserializer extends BaseAvroDeserializer<UserAvro> {
    public UserDeserializer() {
        super(UserAvro.getClassSchema());
    }
}