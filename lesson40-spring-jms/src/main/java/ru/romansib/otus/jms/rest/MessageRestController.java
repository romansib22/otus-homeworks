package ru.romansib.otus.jms.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romansib.otus.jms.service.KafkaProducer;


@RestController
@RequestMapping("/message")
public class MessageRestController {
    private final KafkaProducer kafkaProducer;

    public MessageRestController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    public void postMessage(@RequestBody String body) {
        kafkaProducer.sendMsgKafka(body);
    }
}
