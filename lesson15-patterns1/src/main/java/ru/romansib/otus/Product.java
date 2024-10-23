package ru.romansib.otus;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {
    private final long id;
    private final String title;
    private final String description;
    private final BigDecimal cost;
    private final int weight;
    private final int width;
    private final int length;
    private final int height;

    private Product(long id, String title, String description, BigDecimal cost, int weight, int width, int length, int height) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
        this.width = width;
        this.length = length;
        this.height = height;
    }


    public static final class ProductBuilder {
        private long id;
        private String title;
        private String description;
        private BigDecimal cost;
        private int weight;
        private int width;
        private int length;
        private int height;

        public Product build() {
            return new Product(id, title, description, cost, weight, width, length, height);
        }

        public ProductBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ProductBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }


        public ProductBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public ProductBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public ProductBuilder width(int width) {
            this.width = width;
            return this;
        }

        public ProductBuilder length(int length) {
            this.length = length;
            return this;
        }

        public ProductBuilder height(int height) {
            this.height = height;
            return this;
        }

    }

}
