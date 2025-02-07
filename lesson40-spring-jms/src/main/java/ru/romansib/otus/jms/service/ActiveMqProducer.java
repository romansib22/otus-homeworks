package ru.romansib.otus.jms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.romansib.otus.jms.config.ActiveMqJmsConfig;

import java.util.UUID;

@Slf4j
@Service
public class ActiveMqProducer {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;


    public ActiveMqProducer(@Qualifier(ActiveMqJmsConfig.JMS_TEMPLATE) JmsTemplate jmsTemplate,
                            ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }


    public void convertAndSend(String msg) {
        jmsTemplate.convertAndSend(ActiveMqJmsConfig.DESTINATION_NAME, msg);
        log.info("Produced message {} to {}", msg, ActiveMqJmsConfig.DESTINATION_NAME);
    }
}
