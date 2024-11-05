package ru.romansib.otus;

public class ItemServiceProxyImpl implements ItemServiceProxy {
    private final DataSource dataSource = DataSource.getInstance();

    @Override
    public void generateRecords() {
        ItemService itemService = new ItemService(new ItemDaoImpl(dataSource, dataSource.getNewConnection()));
        itemService.generateRecords();
    }

    @Override
    public void increasePrice() {
        ItemService itemService = new ItemService(new ItemDaoImpl(dataSource, dataSource.getNewConnection()));
        itemService.increasePrice();
    }
    @Override
    public void getAll() {
        ItemService itemService = new ItemService(new ItemDaoImpl(dataSource, dataSource.getNewConnection()));
        itemService.getAll();
    }
}
