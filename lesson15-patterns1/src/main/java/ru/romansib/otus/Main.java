package ru.romansib.otus;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        Product product = new Product.ProductBuilder()
                .id(1)
                .title("title")
                .description("description")
                .cost(new BigDecimal(100))
                .weight(10)
                .width(20)
                .length(30)
                .height(50)
                .build();

        Box box = new Box();
        box.showBoxContent();
    }
}