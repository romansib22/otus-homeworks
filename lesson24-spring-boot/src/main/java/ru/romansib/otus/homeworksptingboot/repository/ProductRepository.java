package ru.romansib.otus.homeworksptingboot.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.romansib.otus.homeworksptingboot.entity.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductRepository {
    private final Map<Long, Product> productTable = new ConcurrentHashMap<>();
    private long productSequence = 1;

    @PostConstruct
    private void initProductTable() {
        for (int i = 0; i < 10; i++) {
            productTable.put(productSequence, Product.builder().id(productSequence).name("Product number " + productSequence).price(new BigDecimal((i + 1) * 21 + 2)).build());
            productSequence++;
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
            p.setId(productSequence);
            productSequence++;
        }
        productTable.put(p.getId(), p);
        return p;
    }

    public void delete(long id) {
        productTable.remove(id);
    }
}
