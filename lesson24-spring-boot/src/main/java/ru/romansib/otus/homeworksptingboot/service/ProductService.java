package ru.romansib.otus.homeworksptingboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romansib.otus.homeworksptingboot.entity.Product;
import ru.romansib.otus.homeworksptingboot.exceptions.ProductIdException;
import ru.romansib.otus.homeworksptingboot.exceptions.ProductNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryService service;

    public List<Product> getAll() {
        return service.getAll();
    }

    public Product getById(long id) {
        Optional<Product> productOpt = service.getById(id);
        return productOpt.orElseThrow(() -> new ProductNotFoundException("Продукт с id " + id + " не найден!"));
    }

    public Product createProduct(Product product) {
        if (product.getId() != null) {
            throw new ProductIdException("В запросе на создание указан id!");
        }
        return service.save(product);
    }

    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            throw new ProductIdException("В запросе на изменение не указан id!");
        }
        Product productInDb = getById(product.getId());
        productInDb.setName(product.getName());
        productInDb.setPrice(product.getPrice());
        return service.save(productInDb);
    }

    public void deleteProduct(Long id) {
        Product p = getById(id);
        service.delete(p.getId());
    }
}
