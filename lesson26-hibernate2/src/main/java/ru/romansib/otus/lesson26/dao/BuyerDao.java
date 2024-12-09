package ru.romansib.otus.lesson26.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.romansib.otus.lesson26.entity.Buyer;

import java.util.List;


public class BuyerDao {

    private final SessionFactory factory;

    public BuyerDao(SessionFactory factory) {
        this.factory = factory;
        init();
    }

    public Buyer findById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            return session.get(Buyer.class, id);
        }
    }

    public void save(Buyer buyer) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(buyer);
            session.getTransaction().commit();
        }
    }

    public void update(Buyer buyer) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Buyer b = session.get(Buyer.class, buyer.getId());
            b.setBoughtProducts(buyer.getBoughtProducts());
            session.getTransaction().commit();
            session.close();
        }
    }

    public void delete(Buyer buyer) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(buyer);
            session.getTransaction().commit();
        }
    }

    public List<Buyer> findAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Buyer> result = session.createQuery("SELECT b FROM Buyer b", Buyer.class).getResultList();
            session.close();
            return result;
        }
    }

    public boolean existsByName(String name) {
        return findAll().stream().anyMatch(b -> b.getName().equals(name));
    }

    public void init() {
        String[] buyerNames = new String[]{"Vasily", "Igor", "Alex", "Bob"};
        for (String name : buyerNames) {
            if (!existsByName(name)) {
                Buyer b = new Buyer();
                b.setName(name);
                save(b);
            }
        }
    }
}
