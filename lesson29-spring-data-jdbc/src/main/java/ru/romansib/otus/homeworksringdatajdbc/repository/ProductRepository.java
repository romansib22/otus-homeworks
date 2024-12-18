package ru.romansib.otus.homeworksringdatajdbc.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.romansib.otus.homeworksringdatajdbc.entity.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProductRepository {
    private final Map<Long, Product> productTable = new ConcurrentHashMap<>();
    private AtomicLong productSequence = new AtomicLong(1);

    @PostConstruct
    private void initProductTable() {
        for (int i = 0; i < 10; i++) {
            long id = productSequence.getAndIncrement();
            productTable.put(id, Product.builder().id(id).name("Product number " + productSequence).price(new BigDecimal((i + 1) * 21 + 2)).build());
        }
    }

    public List<Product> getAll() {
        return Collections.unmodifiableList((List<? extends Product>) productTable.values());
    }

    public Optional<Product> getById(long id) {
        return Optional.ofNullable(productTable.get(id));
    }

    public Product save(Product p) {
        if (p.getId() == null) {
            p.setId(productSequence.getAndIncrement());
        }
        productTable.put(p.getId(), p);
        return p;
    }

    public void delete(long id) {
        productTable.remove(id);
    }
}
