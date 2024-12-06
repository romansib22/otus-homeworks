package ru.romansib.otus.lesson26.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Buyer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductsBuyers> boughtProducts;

    public List<ProductsBuyers> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<ProductsBuyers> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
