package ru.romansib.otus.lesson26;

import ru.romansib.otus.lesson26.configuration.AppSessionFactory;
import ru.romansib.otus.lesson26.dao.BuyerDao;
import ru.romansib.otus.lesson26.dao.ProductDao;
import ru.romansib.otus.lesson26.entity.Buyer;
import ru.romansib.otus.lesson26.entity.Product;
import ru.romansib.otus.lesson26.entity.ProductsBuyers;
import ru.romansib.otus.lesson26.service.MenuService;
import ru.romansib.otus.lesson26.service.ScannerService;

import java.util.List;

public class Main {
    private static BuyerDao buyerDao;
    private static ProductDao productDao;


    public static void main(String[] args) {
        init();
        MenuService service = new MenuService(new ScannerService(), buyerDao, productDao);
        service.showMainMenu();
    }

    private static void init() {
        buyerDao = new BuyerDao(AppSessionFactory.getSessionFactory());
        productDao = new ProductDao(AppSessionFactory.getSessionFactory());

        List<Product> products = productDao.findAll();

        Buyer b1 = buyerDao.findById(1L);
        b1.getBoughtProducts().add(new ProductsBuyers(b1, products.get(0), products.get(0).getPrice()));
        b1.getBoughtProducts().add(new ProductsBuyers(b1, products.get(3),  products.get(3).getPrice()));
        buyerDao.update(b1);

        Buyer b2 = buyerDao.findById(2L);
        b2.getBoughtProducts().add(new ProductsBuyers(b2, products.get(1),  products.get(1).getPrice()));
        b2.getBoughtProducts().add(new ProductsBuyers(b2, products.get(2),  products.get(2).getPrice()));
        buyerDao.update(b2);

        Buyer b3 = buyerDao.findById(3L);
        b3.getBoughtProducts().add(new ProductsBuyers(b3, products.get(1),  products.get(1).getPrice()));
        b3.getBoughtProducts().add(new ProductsBuyers(b3, products.get(3),  products.get(3).getPrice()));
        buyerDao.update(b3);

        Buyer b4 = buyerDao.findById(4L);
        b4.getBoughtProducts().add(new ProductsBuyers(b4, products.get(2),  products.get(2).getPrice()));
        b4.getBoughtProducts().add(new ProductsBuyers(b4, products.get(0),  products.get(0).getPrice()));
        buyerDao.update(b4);


    }


}