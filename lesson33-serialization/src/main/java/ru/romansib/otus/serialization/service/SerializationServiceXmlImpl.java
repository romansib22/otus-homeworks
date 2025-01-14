package ru.romansib.otus.serialization.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.romansib.otus.serialization.model.ResultMap;


public class SerializationServiceXmlImpl implements SerializationService{
    @Override
    public String serialize(ResultMap resultMap) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
