package ru.romansib.otus.homeworksptingboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romansib.otus.homeworksptingboot.entity.Product;
import ru.romansib.otus.homeworksptingboot.exceptions.ProductIdException;
import ru.romansib.otus.homeworksptingboot.exceptions.ProductNotFoundException;
import ru.romansib.otus.homeworksptingboot.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<Product> getAll() {
        return repository.getAll();
    }

    public Optional<Product> getById(long id) {
        Optional<Product> productOpt = repository.getById(id);
        return productOpt;
    }

    public Product createProduct(Product product) {
        if (product.getId() != null) {
            throw new ProductIdException("В запросе на создание указан id!");
        }
        return repository.save(product);
    }

    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            throw new ProductIdException("В запросе на изменение не указан id!");
        }
        Product productInDb = getById(product.getId());
        productInDb.setName(product.getName());
        productInDb.setPrice(product.getPrice());
        return repository.save(productInDb);
    }

    public void deleteProduct(Long id) {
        repository.delete(id);
    }
}
