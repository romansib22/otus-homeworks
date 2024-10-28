package ru.romansib.otus;

import java.util.*;

public class DataSource {
    private static final DataSource INSTANCE = new DataSource();
    private Connection connection;
    private Map<Long, Item> itemTable;
    private final Object transactionLock = new Object();

    private DataSource() {
        this.itemTable = new HashMap<>();
        this.connection = getConnection();
    }

    public static DataSource getInstance() {
        return DataSource.INSTANCE;
    }

    public Connection getConnection() {
        if (connection == null) {
            connection = new Connection();
            System.out.println("New Connection sessionId " + connection.getSessionId());
        }
        return connection;
    }

    public Connection getNewConnection() {
            connection = new Connection();
            System.out.println("New Connection sessionId " + connection.getSessionId());
        return connection;
    }

    public class Connection {
        private final String sessionId;

        public Connection() {
            this.sessionId = generateSessionId();
        }

        public String getSessionId() {
            return sessionId;
        }
    }

    private String generateSessionId() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }


    public void create(Connection c, Item o) {
        synchronized (transactionLock) {
            System.out.println("Create record with id " + o.getId() + " using sessionId " + c.getSessionId());
            itemTable.put(o.getId(), o);
        }
    }

    public void update(Connection c, Item o) {
        if (itemTable.containsKey(o.getId())) {
            synchronized (transactionLock) {
                System.out.println("Update record with id " + o.getId() + " using sessionId " + c.getSessionId());
                itemTable.put(o.getId(), o);
            }
        }
    }

    public void delete(Connection c, Item o) {
        if (itemTable.containsKey(o.getId())) {
            synchronized (transactionLock) {
                System.out.println("Delete record with id " + o.getId() + " using sessionId " + c.getSessionId());
                itemTable.remove(o.getId());
            }
        }
    }

    public void deleteAll(Connection c) {
        synchronized (transactionLock) {
            System.out.println("Delete all records with id using sessionId " + c.getSessionId());
            itemTable = new HashMap<>();
        }
    }

    public Item getById(Connection c, long id) {
        System.out.println("Get record with id " + id + " using sessionId " + c.getSessionId());
        return itemTable.get(id);
    }

    public List<Item> getAll(Connection c) {
        System.out.println("Get all records using sessionId " + c.getSessionId());
        return new ArrayList<>(itemTable.values());
    }
}


