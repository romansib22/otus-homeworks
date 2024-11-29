package ru.romansib.otus.homeworksptingboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romansib.otus.homeworksptingboot.entity.Product;
import ru.romansib.otus.homeworksptingboot.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductRepositoryService {
    private final ProductRepository repository;

    public List<Product> getAll() {
        return repository.getAll();
    }

    public Optional<Product> getById(long id) {
        return  repository.getById(id);
    }

    public Product save(Product p) {
        return repository.save(p);
    }

    public void delete(long id) {
        repository.delete(id);
    }

}
