package ru.romansib.otus.homeworksringdatajdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.romansib.otus.homeworksringdatajdbc.dto.ProductDto;
import ru.romansib.otus.homeworksringdatajdbc.exceptions.ProductIdException;
import ru.romansib.otus.homeworksringdatajdbc.exceptions.ProductNotFoundException;
import ru.romansib.otus.homeworksringdatajdbc.mapper.ProductMapper;
import ru.romansib.otus.homeworksringdatajdbc.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @GetMapping()
    public List<ProductDto> getAllProducts() {
        return mapper.toDto(service.getAll());
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        try {
            return mapper.toDto(service.getById(id).orElseThrow(() -> new ProductNotFoundException("Продукт с id " + id + " не найден!")));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        try {
            return mapper.toDto(service.createProduct(mapper.fromDto(dto)));
        } catch (ProductIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto dto) {
        try {
            return mapper.toDto(service.updateProduct(mapper.fromDto(dto)));
        } catch (ProductIdException pie) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, pie.getMessage());
        } catch (ProductNotFoundException pne) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, pne.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        try {
            service.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
