package ru.romansib.otus.serialization.service;

import ru.romansib.otus.serialization.model.Result;
import ru.romansib.otus.serialization.model.ResultMap;

import java.util.List;


public class SerializationServiceCsvImpl implements SerializationService{
    @Override
    public String serialize(ResultMap resultMap) {
       StringBuilder sb = new StringBuilder();
        //тут, по сути, кастом
        sb.append("chat_identifier;").append("last;").append("belong_number;").append("send_date;").append("text").append(System.getProperty("line.separator"));
        for (List<Result> results : resultMap.getResult().values()) {
            results.forEach(r -> {
                sb.append(r.getChatIdentifier()).append(";").append(r.getLast()).append(";").append(r.getBelongNumber()).append(";");
                sb.append(r.getSendDate()).append(";").append(r.getText()).append(System.getProperty("line.separator"));
            });
        }
        return sb.toString();
    }
}
