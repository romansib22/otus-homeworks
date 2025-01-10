package ru.romansib.otus.serialization.service;

import ru.romansib.otus.serialization.model.ResultMapJavaSerialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SerializationServiceJava {

    public String serialize(ResultMapJavaSerialization resultMap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(resultMap);
            return baos.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
