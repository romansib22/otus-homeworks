package ru.romansib.otus.jms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class KafkaProducer {
    private AtomicInteger keySuffix = new AtomicInteger(0);

    @Value("${topic.mytopic}")
    private String mytopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMsgKafka(String message) {
        kafkaTemplate.send(mytopic, "messageKey" + keySuffix.incrementAndGet(), message);
    }

}
