package ru.romansib.otus.jms.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romansib.otus.jms.service.ActiveMqProducer;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageRestController {
    private final ActiveMqProducer activeMqProducer;

    @PostMapping()
    public void postMessage2(@RequestBody String body) {
        activeMqProducer.convertAndSend(body);
    }
}
