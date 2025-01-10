package ru.romansib.otus.serialization.service;

import ru.romansib.otus.serialization.model.ResultMap;

public interface SerializationService {
    String serialize(ResultMap resultMap);
}
