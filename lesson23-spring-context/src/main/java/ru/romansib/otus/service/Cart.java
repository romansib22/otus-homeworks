package ru.romansib.otus.service;

import ru.romansib.otus.Product;

public interface Cart {
    void showCart();
    void addToCart(Product product);
}
