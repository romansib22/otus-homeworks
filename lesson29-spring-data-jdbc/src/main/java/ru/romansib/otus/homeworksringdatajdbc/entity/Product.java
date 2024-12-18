package ru.romansib.otus.homeworksringdatajdbc.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}
