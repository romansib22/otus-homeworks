package ru.romansib.otus.lesson26.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.romansib.otus.lesson26.entity.Buyer;
import ru.romansib.otus.lesson26.entity.Product;
import ru.romansib.otus.lesson26.entity.ProductsBuyers;

import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class AppSessionFactory {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties props = new Properties();
                props.put("hibernate.connection.driver_class","org.h2.Driver");
                props.put("hibernate.connection.url","jdbc:h2:file:./lesson24db;MODE=PostgreSQL");
                props.put("hibernate.current_session_context_class","thread");
                props.put("hibernate.hbm2ddl.auto","create");

                configuration.setProperties(props);
                configuration.addAnnotatedClass(ProductsBuyers.class);
                configuration.addAnnotatedClass(Buyer.class);
                configuration.addAnnotatedClass(Product.class);
                ServiceRegistry serviceRegistry =new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }
        return sessionFactory;
    }
}
