package ru.romansib.otus.homeworksptingboot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}
