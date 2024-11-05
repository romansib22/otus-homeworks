package ru.romansib.otus;

public class Main {
    public static void main(String[] args) {

        ItemService service = new ItemService(new ItemDaoImpl(DataSource.getInstance(), DataSource.getInstance().getConnection()));

        System.out.println("Inserting items");
        service.generateRecords();
        System.out.println("Check items after insert");
        service.getAll();

        System.out.println("Increasing price");
        service.increasePrice();
        System.out.println("Check items after increasing price");
        service.getAll();

        System.out.println("Delete all records");
        service.deleteAll();
        System.out.println("Check items after deleting all");
        service.getAll();

        System.out.println("Inserting items with proxy class");
        ItemServiceProxy itemServiceProxy = new ItemServiceProxyImpl();
        itemServiceProxy.generateRecords();
        System.out.println("Check items after insert with proxy class");
        itemServiceProxy.getAll();

        System.out.println("Increasing price with proxy class");
        itemServiceProxy.increasePrice();
        System.out.println("Check items after increasing price with proxy class");
        itemServiceProxy.getAll();
    }
}