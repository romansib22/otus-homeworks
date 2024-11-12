package ru.romansib.otus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MigrationHistoryDao {

    private DataSource dataSource;

    public MigrationHistoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean hasRecordWithFilename(String filename) {
        try (ResultSet rs = dataSource.getStatement().executeQuery("select count(1) from migration_history where filename = '" + filename + "'")) {
            if (rs.next() != false) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void executeMigration(String sql) {
        try {
            dataSource.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new ORMException("Не удаось выполнить миграцию, скрипт " + sql);
        }
    }

    public void save(String filename) {
        try {
            dataSource.getStatement().executeUpdate(String.format("insert into migration_history (filename) values ('%s');", filename));
        } catch (SQLException e) {
            throw new ORMException("Не удаось сохранить данные о миграции");
        }
    }

    public List<MigrationHistory> getAllRecords() {
        List<MigrationHistory> result = new ArrayList<>();
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from db_migrator")) {
            while (rs.next() != false) {
                result.add(new MigrationHistory(rs.getLong("id"), rs.getString("filename")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(result);
    }
}
