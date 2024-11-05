package ru.romansib.otus;

import java.math.BigDecimal;
import java.util.List;

public class ItemService {
    private final ItemDao dao;

    public ItemService(ItemDao dao) {
        this.dao = dao;
    }

    public void generateRecords() {
        for (int i = 0; i < 100; i++) {
            Item item = new Item.ItemBuilder()
                    .id(i)
                    .title("Record " + i)
                    .price(new BigDecimal(100 * i +5))
                    .build();
            dao.create(item);
        }
    }

    public void increasePrice() {
        for (Item i : dao.getAll()) {
            i.setPrice(i.getPrice().multiply(new BigDecimal(2)));
            dao.update(i);
        }
    }

    public void getAll() {
        List<Item> allRecords = dao.getAll();
        for (Item i : allRecords) {
            System.out.println(i.toString());
        }
        if (allRecords.isEmpty()) {
            System.out.println("No records in table");
        }
    }

    public void deleteAll() {
        dao.deleteAll();
    }
}
