package ru.romansib.otus.service;

import lombok.ToString;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.romansib.otus.Product;

import java.util.ArrayList;
import java.util.List;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@ToString
public class CartImpl implements Cart {
    private List<Product> content = new ArrayList<>();

    @Override
    public void showCart() {
        content.forEach(System.out::println);
    }

    @Override
    public void addToCart(Product product) {
        content.add(product);
        System.out.println(product.getName() + " added to cart");
    }
}
