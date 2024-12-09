package ru.romansib.otus.lesson26.service;

import ru.romansib.otus.lesson26.dao.BuyerDao;
import ru.romansib.otus.lesson26.dao.ProductDao;
import ru.romansib.otus.lesson26.entity.Buyer;
import ru.romansib.otus.lesson26.entity.Product;
import ru.romansib.otus.lesson26.entity.ProductsBuyers;

import java.util.List;

public class MenuService {
    private final ScannerService scannerService;
    private final BuyerDao buyerDao;
    private final ProductDao productDao;

    public MenuService(ScannerService scannerService, BuyerDao buyerDao, ProductDao productDao) {
        this.scannerService = scannerService;
        this.buyerDao = buyerDao;
        this.productDao = productDao;
    }


    public void showMainMenu() {
        System.out.println("1 - Show buyers list");
        System.out.println("2 - Show product list");
        System.out.println("3 - Show products that buyer has bought");
        System.out.println("4 - Show the buyers who bought the product");
        System.out.println("5 - Delete buyer");
        System.out.println("6 - Delete product");
        System.out.println("7 - Exit");
        processMainMenuItem(scannerService.getInt("Please choose menu item"));
    }

    private void processMainMenuItem(int menuItem) {
        boolean exit = false;
        switch (menuItem) {
            case 1:
                showAllBuyers();
                break;
            case 2:
                showAllProducts();
                break;
            case 3:
                showBoughtProducts();
                break;
            case 4:
                showBuyersOfProduct();
                break;
            case 5:
                deleteBuyer();
                break;
            case 6:
                deleteProduct();
                break;
            case 7:
                exit = true;
        }
        if (!exit)
            showMainMenu();
    }

    private void showAllBuyers() {
        for (Buyer p : buyerDao.findAll()) {
            System.out.println(p.toString());
        }
    }

    private void showAllProducts() {
        for (Product p : productDao.findAll()) {
            System.out.println(p.toString());
        }
    }

    private int showItemIdRequest(String message) {
        return scannerService.getInt(message);
    }

    private void showBoughtProducts() {
        int buyerId = showItemIdRequest("Input buyer id");
        Buyer buyer = buyerDao.findById(buyerId);
        if (buyer == null) {
            System.out.println("Buyer with id " + buyerId + " not found");
            return;
        }
        List<ProductsBuyers> products = buyer.getBoughtProducts();
        products.forEach(System.out::println);
    }

    private void showBuyersOfProduct() {
        int productId = showItemIdRequest("Input product id");
        Product product = productDao.findById(productId);
        if (product == null) {
            System.out.println("Product with id " + productId + " not found");
            return;
        }
        List<ProductsBuyers> buyers = product.getBuyersWhoBought();
        buyers.forEach(System.out::println);
    }

    private void deleteBuyer() {
        int buyerId = showItemIdRequest("Input buyer id");
        Buyer buyer = buyerDao.findById(buyerId);
        if (buyer == null) {
            System.out.println("Buyer with id " + buyerId + " not found");
            return;
        }
        buyerDao.delete(buyer);
    }

    private void deleteProduct() {
        int productId = showItemIdRequest("Input product id");
        Product product = productDao.findById(productId);
        if (product == null) {
            System.out.println("Product with id " + productId + " not found");
            return;
        }
        productDao.delete(product);
    }
}
