package ru.romansib.otus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DbMigrator {
    private DataSource dataSource;
    private MigrationHistoryDao migrationHistoryDAO;

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
        this.migrationHistoryDAO = new MigrationHistoryDao(dataSource);
    }

    private void init() {
        try {
            DatabaseMetaData meta = dataSource.getConnection().getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "MIGRATION_HISTORY", new String[]{"TABLE"});
            if (!resultSet.next()) {
                dataSource.getStatement().executeUpdate(
                        "create table if not exists migration_history (id bigserial primary key, filename varchar(255))"
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void migrate() {
        init();
        Map<String, String> migrationScripts = readMigrationFiles();
        for (Map.Entry<String, String> e : migrationScripts.entrySet()) {
            migrationHistoryDAO.executeMigration(e.getValue());
            migrationHistoryDAO.save(e.getKey());
            System.out.println("Миграция " + e.getKey() + " выполнена успешно.");
        }
        //System.out.println(MigrationHistoryDao.getAllRecords());
    }

    private Map<String, String> readMigrationFiles() {
        Map<String, String> result = new HashMap<>();
        File dir = new File(getClass().getClassLoader().getResource("migration").getFile());
        File[] files = dir.listFiles();
        for (File f : files) {
            try {
                InputStream is = new FileInputStream(f);
                String sqlScript = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                if (!checkMigrationIsDone(f.getName())) {
                    result.put(f.getName(), sqlScript);
                }
            } catch (FileNotFoundException e) {
                throw new ORMException("Не удалось найти файл " + f);
            } catch (IOException e) {
                throw new ORMException("Не удалось прочитать файл " + f);
            }
        }
        return result;
    }

    private boolean checkMigrationIsDone(String filename) {
        return migrationHistoryDAO.hasRecordWithFilename(filename);
    }


}
