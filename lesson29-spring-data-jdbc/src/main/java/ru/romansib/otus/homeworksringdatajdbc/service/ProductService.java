package ru.romansib.otus.homeworksringdatajdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romansib.otus.homeworksringdatajdbc.entity.Product;
import ru.romansib.otus.homeworksringdatajdbc.exceptions.ProductIdException;
import ru.romansib.otus.homeworksringdatajdbc.exceptions.ProductNotFoundException;
import ru.romansib.otus.homeworksringdatajdbc.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getById(long id) {
        Optional<Product> productOpt = repository.findById(id);
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
        Optional<Product> productInDbOpt = getById(product.getId());
        if (!productInDbOpt.isPresent()) {
            throw new ProductNotFoundException("Не найден продукт с id = " + product.getId());
        }
        productInDbOpt.get().setName(product.getName());
        productInDbOpt.get().setPrice(product.getPrice());
        return repository.save(productInDbOpt.get());
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
