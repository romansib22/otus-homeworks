package ru.romansib.otus;

@RepositoryTable(title = "migration_history")
public class MigrationHistory {
    @RepositoryIdField
    private Long id;
    @RepositoryField
    private String filename;

    public MigrationHistory(Long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "MigrationHistory{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                '}';
    }
}
