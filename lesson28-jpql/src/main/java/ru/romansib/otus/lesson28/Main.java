package ru.romansib.otus.lesson28;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romansib.otus.lesson28.entity.Address;
import ru.romansib.otus.lesson28.entity.Client;
import ru.romansib.otus.lesson28.entity.Phone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static final String PERSISTENCE_UNIT_NAME = "SingleUnit";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public static void main(String[] args) {
        /*Добавление записи в таблицу Clients c адресом и телефонами*/
        addClientRecord();
        /*Выводим данные по клиенту*/
        showClient();
        /*меняем данные*/
        changeAddressAndDeletePhone();
        /*Выводим данные по клиенту*/
        showClient();
    }

    private static void addClientRecord() {
        EntityManager entityManager = emf.createEntityManager();
        Client client = new Client("FirstClient");
        Address address = new Address("Lenina str", client);
        client.setAddress(address);
        List<Phone> phones = Arrays.asList(new Phone("12345678", address), new Phone("098765432", address),  new Phone("11220099", address));
        client.getAddress().setPhones(phones);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(client);
        transaction.commit();
        entityManager.close();
    }

    private static List<Client> getClients() {
        EntityManager entityManager = emf.createEntityManager();
        List<Client> clientList = entityManager
                .createQuery("SELECT C FROM Client C LEFT JOIN FETCH C.address LEFT JOIN FETCH C.address.phones", Client.class)
                .getResultList();
        entityManager.close();
        return clientList;
    }

    private static void showClient() {
        List<Client> clientList = getClients();
        for (Client client : clientList) {
            LOGGER.info("{}", client);
        }
    }

    private static void changeAddressAndDeletePhone(){
        EntityManager entityManager = emf.createEntityManager();
        List<Client> clientList = entityManager
                .createQuery("SELECT C FROM Client C LEFT JOIN FETCH C.address LEFT JOIN FETCH C.address.phones", Client.class)
                .getResultList();
        Client myClient = clientList.get(0);
        myClient.getAddress().setStreet("Stalina st");
        myClient.getAddress().getPhones().forEach(p -> {
            if (p.getNumber().equals("11220099"))
                p.setAddress(null);
        });

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(myClient);
        transaction.commit();
        entityManager.close();
    }

}