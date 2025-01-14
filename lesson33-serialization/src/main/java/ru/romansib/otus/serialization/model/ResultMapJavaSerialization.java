package ru.romansib.otus.serialization.model;

import lombok.Getter;
import lombok.Setter;
import ru.romansib.otus.serialization.service.SerializationServiceJava;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
public class ResultMapJavaSerialization implements Serializable {
    private transient final SerializationServiceJava serializationService;

    public ResultMapJavaSerialization(SerializationServiceJava serializationService) {
        this.serializationService = serializationService;
    }


    public String serialize() {
        return serializationService.serialize(this);
    }


    @Getter
    private Map<String, List<ResultJavaSerialization>> result;

}
