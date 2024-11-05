package ru.romansib.otus;

import java.util.List;

public class ItemDaoImpl implements ItemDao{
    private final DataSource dataSource;
    private final DataSource.Connection connection;

    public ItemDaoImpl(DataSource dataSource, DataSource.Connection connection) {
        this.dataSource = dataSource;
        this.connection = connection;
    }

    @Override
    public void create(Item item) {
        dataSource.create(connection, item);
    }

    @Override
    public void update(Item item) {
        dataSource.update(connection, item);
    }

    @Override
    public void delete(Item item) {
        dataSource.delete(connection, item);
    }
    @Override
    public void deleteAll() {
        dataSource.deleteAll(connection);
    }
    @Override
    public Item getById(long id) {
        return dataSource.getById(connection, id);
    }

    @Override
    public List<Item> getAll() {
        return dataSource.getAll(connection);
    }


}
