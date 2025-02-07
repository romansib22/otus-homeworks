package ru.romansib.otus.jms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.romansib.otus.jms.config.ActiveMqJmsConfig;

import java.io.Serializable;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveMqConsumer {
    private final ObjectMapper objectMapper;

    @JmsListener(destination = ActiveMqJmsConfig.DESTINATION_NAME
            , containerFactory = ActiveMqJmsConfig.JMS_LISTENER_CONTAINER_FACTORY)
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage)
                onTextMessage((TextMessage) message);
            else if (message instanceof ObjectMessage)
                onObjectMessage((ObjectMessage) message);
            else
                throw new IllegalArgumentException("Message Error");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void onTextMessage(TextMessage message) throws JMSException {
        String msg = message.getText();
        log.info("Consumed message {}", msg);
    }

    private void onObjectMessage(ObjectMessage message)
            throws JMSException, ClassNotFoundException, JsonProcessingException {
        String className = message.getStringProperty(ActiveMqJmsConfig.CLASS_NAME);

        if (className == null)
            onSerializableObjectMessage(message);
        else
            onCustomObjectMessage(Class.forName(className), message);
    }

    private static void onSerializableObjectMessage(ObjectMessage message) throws JMSException {
        Serializable obj = message.getObject();
        log.info("Consumed serializable message {}", obj);
    }

    private void onCustomObjectMessage(Class<?> cls, ObjectMessage message)
            throws JMSException, JsonProcessingException {
        String json = String.valueOf(message.getObject());
        Object obj = objectMapper.readValue(json, cls);
        log.info("Consumed custom message {}, {}", cls.getSimpleName(), obj);
    }
}
