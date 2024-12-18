package ru.romansib.otus.homeworksringdatajdbc.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.romansib.otus.homeworksringdatajdbc.entity.Product;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
