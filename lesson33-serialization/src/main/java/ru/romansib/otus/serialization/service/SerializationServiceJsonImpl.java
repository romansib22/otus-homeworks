package ru.romansib.otus.serialization.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.romansib.otus.serialization.model.ResultMap;

public class SerializationServiceJsonImpl implements SerializationService {
    @Override
    public String serialize(ResultMap resultMap) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
