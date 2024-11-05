package ru.romansib.otus;

import java.math.BigDecimal;

public class Item {
    private long id;
    private String title;
    private BigDecimal price;

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private Item(long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public static final class ItemBuilder {
        private long id;
        private String title;
        private BigDecimal price;

        public Item build() {
            return new Item(id, title, price);
        }

        public ItemBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ItemBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ItemBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
