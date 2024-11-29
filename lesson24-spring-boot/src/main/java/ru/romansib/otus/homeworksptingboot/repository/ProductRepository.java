package ru.romansib.otus.homeworksptingboot.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.romansib.otus.homeworksptingboot.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return new ArrayList<>(productTable.values());
    }

    public Optional<Product> getById(long id) {
        if (productTable.containsKey(id)) {
            return Optional.of(productTable.get(id));
        }
        return Optional.empty();
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
