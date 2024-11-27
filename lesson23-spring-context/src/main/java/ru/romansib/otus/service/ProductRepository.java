package ru.romansib.otus.service;

import ru.romansib.otus.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAllProducts();
    Optional<Product> getProductById(long id);
}
