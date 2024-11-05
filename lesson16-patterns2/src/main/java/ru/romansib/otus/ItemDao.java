package ru.romansib.otus;

import java.util.List;

public interface ItemDao {
    void create(Item item);
    void update(Item item);
    void delete(Item item);
    void deleteAll();
    Item getById(long id);
    List<Item> getAll();
}
