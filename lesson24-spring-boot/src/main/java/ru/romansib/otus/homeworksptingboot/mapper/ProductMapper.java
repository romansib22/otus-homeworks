package ru.romansib.otus.homeworksptingboot.mapper;

import org.springframework.stereotype.Component;
import ru.romansib.otus.homeworksptingboot.dto.ProductDto;
import ru.romansib.otus.homeworksptingboot.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        return dto;
    }

    public List<ProductDto> toDto(List<Product> src) {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product p : src) {
            dtos.add(toDto(p));
        }
        return dtos;
    }

    public Product fromDto(ProductDto dto) {
        return Product.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice()).build();
    }
}
