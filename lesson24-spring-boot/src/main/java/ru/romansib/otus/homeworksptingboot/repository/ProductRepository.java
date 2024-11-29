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
    private Map<Long, Product> productTable = new ConcurrentHashMap<>();

    @PostConstruct
    private void initProductTable() {
        for (int i = 0; i < 10; i++) {
            productTable.put((i + 1L), Product.builder().id(i + 1L).name("Product number " + i + 1).price(new BigDecimal((i + 1) * 21 + 2)).build());
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
            p.setId((long) productTable.size() + 1);
        }
        productTable.put(p.getId(), p);
        return p;
    }

    public void delete(long id) {
        productTable.remove(id);
    }
}
