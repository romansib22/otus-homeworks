package ru.romansib.otus.service;

import org.springframework.stereotype.Component;
import ru.romansib.otus.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private List<Product> productList = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            Product.builder().build();
            productList.add(Product.builder()
                    .id((long) (i + 1))
                    .name("Product " + i + 1)
                    .price(new BigDecimal(99 * i + 1))
                    .build());
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Optional<Product> getProductById(long id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }
}
