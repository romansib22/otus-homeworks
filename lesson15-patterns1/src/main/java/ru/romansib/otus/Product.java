package ru.romansib.otus;

import lombok.Getter;

@Getter
public class Product {
    private final long id;
    private final String title;
    private final String description;
    private final Float cost;
    private final int weight;
    private final int width;
    private final int length;
    private final int height;

    private Product(long id, String title, String description, Float cost, int weight, int width, int length, int height) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
        this.width = width;
        this.length = length;
        this.height = height;
    }


    public interface ProductBuilderI {
        ProductBuilder id(int id);

        ProductBuilder title(String title);

        ProductBuilder description(String description);

        ProductBuilder cost(Float cost);

        ProductBuilder weight(int weight);

        ProductBuilder width(int width);

        ProductBuilder length(int length);

        ProductBuilder height(int height);
    }

    public static final class ProductBuilder implements ProductBuilderI {
        private long id;
        private String title;
        private String description;
        private Float cost;
        private int weight;
        private int width;
        private int length;
        private int height;

        public Product build() {
            return new Product(id, title, description, cost, weight, width, length, height);
        }

        @Override
        public ProductBuilder id(int id) {
            this.id = id;
            return this;
        }

        @Override
        public ProductBuilder title(String title) {
            this.title = title;
            return this;
        }

        @Override
        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public ProductBuilder cost(Float cost) {
            this.cost = cost;
            return this;
        }

        @Override
        public ProductBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        @Override
        public ProductBuilder width(int width) {
            this.width = width;
            return this;
        }

        @Override
        public ProductBuilder length(int length) {
            this.length = length;
            return this;
        }

        @Override
        public ProductBuilder height(int height) {
            this.height = height;
            return this;
        }

    }

}
