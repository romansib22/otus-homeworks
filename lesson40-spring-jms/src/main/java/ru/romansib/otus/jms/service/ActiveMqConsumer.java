package ru.romansib.otus.jms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.romansib.otus.jms.config.ActiveMqJmsConfig;

import java.io.Serializable;

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
        System.out.format("[Received message] : %s\n", msg);
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
        System.out.format("[Received serializable message] : %s\n", obj);
    }

    private void onCustomObjectMessage(Class<?> cls, ObjectMessage message)
            throws JMSException, JsonProcessingException {
        String json = String.valueOf(message.getObject());
        Object obj = objectMapper.readValue(json, cls);
        System.out.format("[Received custom message: %s] : %s\n", cls.getSimpleName(), obj);
    }
}
