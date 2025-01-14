package ru.romansib.otus.serialization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romansib.otus.serialization.model.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MainService {
    private final DeserializationService deserializationService;

    public String getResultMap(String acceptHeader) {
        return switch (acceptHeader) {
            case "application/xml" -> getResultMap(new ResultMap(new SerializationServiceXmlImpl()));
            case "application/csv" -> getResultMap(new ResultMap(new SerializationServiceCsvImpl()));
            case "application/java" -> getResultMapJavaSerialization();
            default -> getResultMap(new ResultMap(new SerializationServiceJsonImpl()));
        };
    }

    private String getResultMap(ResultMap resultMap) {
        Sms sms = deserializationService.deserialize();
        Map<String, List<Result>> result = new HashMap<>();
        for (ChatSession chatSession : sms.getChatSessions()) {
            for (Message m : chatSession.getMessages()) {
                List<Result> resultList = result.getOrDefault(m.getBelongNumber(), new ArrayList<>());
                resultList.add(Result.builder()
                        .chatIdentifier(chatSession.getChatIdentifier())
                        .last(chatSession.getMembers().get(0).getLast())
                        .belongNumber(m.getBelongNumber())
                        .sendDate(m.getSendDate())
                        .text(m.getText())
                        .date(m.getDate())
                        .build());
                resultList.sort(Comparator.comparingLong(Result::getDate));
                result.put(m.getBelongNumber(), resultList);
            }
        }
        resultMap.setResult(result);
        return resultMap.serialize();
    }

    public String getResultMapJavaSerialization() {
        ResultMapJavaSerialization resultMap = new ResultMapJavaSerialization(new SerializationServiceJava());

        Sms sms = deserializationService.deserialize();
        Map<String, List<ResultJavaSerialization>> result = new HashMap<>();
        for (ChatSession chatSession : sms.getChatSessions()) {
            for (Message m : chatSession.getMessages()) {
                List<ResultJavaSerialization> resultList = result.getOrDefault(m.getBelongNumber(), new ArrayList<>());
                resultList.add(ResultJavaSerialization.builder()
                        .chatIdentifier(chatSession.getChatIdentifier())
                        .last(chatSession.getMembers().get(0).getLast())
                        .belongNumber(m.getBelongNumber())
                        .sendDate(m.getSendDate())
                        .text(m.getText())
                        .date(m.getDate())
                        .build());
                resultList.sort(Comparator.comparingLong(ResultJavaSerialization::getDate));
                result.put(m.getBelongNumber(), resultList);
            }
        }
        resultMap.setResult(result);
        return resultMap.serialize();
    }
}
