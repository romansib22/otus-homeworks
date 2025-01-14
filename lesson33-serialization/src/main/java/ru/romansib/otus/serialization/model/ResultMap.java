package ru.romansib.otus.serialization.model;

import lombok.Getter;
import lombok.Setter;
import ru.romansib.otus.serialization.service.SerializationService;

import java.util.List;
import java.util.Map;

@Setter
public class ResultMap {
    private transient final SerializationService serializationService;

    public ResultMap(SerializationService serializationService) {
        this.serializationService = serializationService;
    }


    public String serialize() {
        return serializationService.serialize(this);
    }

    @Getter
    private Map<String, List<Result>> result;

}
