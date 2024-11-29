package ru.romansib.otus.homeworksptingboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.romansib.otus.homeworksptingboot.dto.ProductDto;
import ru.romansib.otus.homeworksptingboot.exceptions.ProductIdException;
import ru.romansib.otus.homeworksptingboot.exceptions.ProductNotFoundException;
import ru.romansib.otus.homeworksptingboot.mapper.ProductMapper;
import ru.romansib.otus.homeworksptingboot.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return mapper.toDto(service.getAll());
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable(name = "id") Long id) {
        try {
            return mapper.toDto(service.getById(id));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        try {
            return mapper.toDto(service.createProduct(mapper.fromDto(dto)));
        } catch (ProductIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update")
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
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        try {
            service.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
