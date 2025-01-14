package ru.romansib.otus.serialization.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.romansib.otus.serialization.model.Sms;
import ru.romansib.otus.serialization.utils.FileUtil;


@Service
public class DeserializationService {
    public Sms deserialize() {
        String fileContent = FileUtil.getResourceFileAsString("sms.json");
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(fileContent, Sms.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
