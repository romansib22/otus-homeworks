package ru.romansib.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.romansib.otus.Product;
import ru.romansib.otus.exceptions.ProductNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final ProductRepository productRepository;
    private final ScannerService scannerService;
    private Cart cart;

    @Override
    public void startSession(ApplicationContext context) {
        this.cart = (Cart) context.getBean("cartImpl");
        showMainMenu();
    }

    private void showMainMenu() {
        System.out.println("1 - Show products");
        System.out.println("2 - Find product by id");
        System.out.println("3 - Add product to cart by id");
        System.out.println("4 - Show cart");
        System.out.println("5 - End session");
        processMainMenuItem(scannerService.getInt("Please choose menu item"));
    }

    private void processMainMenuItem(int menuItem) {
        boolean exit = false;
        switch (menuItem) {
            case 1:
                showAllProducts();
                break;
            case 2:
                processGetByIdMenuItem();
                break;
            case 3:
                addToCart();
                break;
            case 4:
                showCart();
                break;
            case 5:
                exit = true;
        }
        if (!exit)
            showMainMenu();
    }

    private void showAllProducts() {
        for (Product p : productRepository.getAllProducts()) {
            System.out.println(p);
        }
    }

    private int showProductChoose() {
        return scannerService.getInt("Input product id");
    }

    private void processGetByIdMenuItem() {
        int productId = showProductChoose();
        Optional<Product> productOpt = productRepository.getProductById(productId);
        if (productOpt.isPresent()) {
            System.out.println(productOpt.get());
        } else {
            throw new ProductNotFoundException("Product with id " + productId + " not found!");
        }
    }

    private void addToCart() {
        int productId = showProductChoose();
        Optional<Product> productOpt = productRepository.getProductById(productId);
        if (productOpt.isPresent()) {
            cart.addToCart(productOpt.get());
        } else {
            throw new ProductNotFoundException("Product with id " + productId + " not found!");
        }
    }

    private void showCart() {
        System.out.println("Cart contains:");
        cart.showCart();
    }
}
