package ru.romansib.otus.lesson26.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.romansib.otus.lesson26.entity.Product;

import java.math.BigDecimal;
import java.util.List;


public class ProductDao {

    private final SessionFactory factory;

    public ProductDao(SessionFactory factory) {
        this.factory = factory;
        init();
    }

    public Product findById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            return session.get(Product.class, id);
        }
    }

    public void save(Product product) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    public void delete(Product product) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    public List<Product> findAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Product> result = session.createQuery("SELECT b FROM Product b", Product.class).getResultList();
            session.close();
            return result;
        }
    }

    public boolean existsByName(String name) {
        return findAll().stream().anyMatch(b -> b.getName().equals(name));
    }

    private void init() {
        String[] productNames = new String[]{"Spoon", "Fork", "Knife", "Cup"};
        BigDecimal[] productPrices = new BigDecimal[]{new BigDecimal("20.0"),new BigDecimal("21.2"),new BigDecimal("50.0"), new BigDecimal("120.0")};
        for (int i = 0; i < productNames.length; i++) {
            if (!existsByName(productNames[i])) {
                Product b = new Product();
                b.setName(productNames[i]);
                b.setPrice(productPrices[i]);
                save(b);
            }
        }
    }
}
