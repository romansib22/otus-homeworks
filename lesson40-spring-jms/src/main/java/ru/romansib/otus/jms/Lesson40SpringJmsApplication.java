package ru.romansib.otus.jms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Lesson40SpringJmsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Lesson40SpringJmsApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
//        Connection connection = null;
//        try {
//            // Producer
//            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//                    "tcp://localhost:61616");
//
//            connection = connectionFactory.createConnection();
//            Session session = connection.createSession(false,
//                    Session.AUTO_ACKNOWLEDGE);
//            Queue queue = session.createQueue("customerQueue");
//            MessageProducer producer = session.createProducer(queue);
//            String payload = "SomeTask";
//            Message msg = session.createTextMessage(payload);
//            System.out.println("Sending text '" + payload + "'");
//            producer.send(msg);
//            session.close();
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }
    }
}
