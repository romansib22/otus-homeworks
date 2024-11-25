package ru.romansib.otus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class Product {
    Long id;
    String name;
    BigDecimal price;
}
