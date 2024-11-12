package ru.romansib.otus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BonusService {
    private DataSource dataSource;

    public BonusService(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void createBonus(String login, int amount) {
        // dataSource.getStatement()...
    }
}
