package ru.romansib.otus.lesson26.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products_buyers")
@Getter
@Setter
public class ProductsBuyers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal historyPrice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductsBuyers(Buyer buyer, Product product, BigDecimal historyPrice) {
        this.buyer = buyer;
        this.product = product;
        this.historyPrice = historyPrice;
    }

    public ProductsBuyers() {
    }

    @Override
    public String toString() {
        return "ProductsBuyers{" +
                "historyPrice=" + historyPrice +
                ", buyer=" + buyer.getName() +
                ", product=" + product.getName() +
                '}';
    }
}
