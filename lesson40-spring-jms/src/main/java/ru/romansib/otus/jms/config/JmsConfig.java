//package ru.romansib.otus.jms.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.core.JmsTemplate;
//
//import javax.print.attribute.standard.Destination;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class JmsConfig {
//    private static final String BROKER_URL="tcp://localhost:9092";
//    public static final String MY_TOPIC = "local.otus";
//
//    private static final String CONNECTION_FACTORY = "kafkaConnectionFactory";
//    public static final String JMS_TEMPLATE = "kafkaJmsTemplate";
//
//
//    @Bean(JMS_TEMPLATE)
//    public JmsTemplate jmsTemplate() {
//        JmsTemplate jmsTemplate = new JmsTemplate() {
//            @Override
//            public void convertAndSend(Destination destination, final Object message) {
//
//            }
//
//        };
//        jmsTemplate.setReceiveTimeout(TimeUnit.SECONDS.toMillis(10));
//        JmsTemplate jmsTemplate1 = new JmsTemplate()
//
//        return jmsTemplate;
//    }
//}
