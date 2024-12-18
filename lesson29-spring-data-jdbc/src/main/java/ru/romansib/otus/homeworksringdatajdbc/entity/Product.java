package ru.romansib.otus.homeworksringdatajdbc.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@Table(name = "PRODUCTS")
public class Product {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
}
