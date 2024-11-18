package ru.romansib.otus;

import java.sql.SQLException;

public class MockChatServer {
    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            System.out.println("Сервер чата запущен");
            dataSource = new DataSource("jdbc:h2:file:./db;MODE=PostgreSQL");
            dataSource.connect();

            DbMigrator migrator = new DbMigrator(dataSource);
            migrator.migrate();


            AbstractRepository<User> usersRepository = new AbstractRepository<>(dataSource, User.class);
            usersRepository.save(new User(null, "A", "A", "A"));
            usersRepository.save(new User(null, "B", "B", "B"));
            usersRepository.save(new User(null, "C", "C", "C"));

            System.out.println("----------FIND BY ID_______");
            User user = usersRepository.findById(1L);
            System.out.println(user.toString());

            System.out.println("----------FIND ALL_______");
            for (User u : usersRepository.findAll()) {
                System.out.println(u.toString());
            }

            System.out.println("----------UPDATE_______");
            User user0 = usersRepository.findById(2L);
            System.out.println(user0.toString());
            User u = new User(2L,"C","C","A");
            usersRepository.update(u);
            user0 = usersRepository.findById(2L);
            System.out.println(user0.toString());


            System.out.println("----------DELETE_______");
            usersRepository.deleteById(3L);
            for (User u0 : usersRepository.findAll()) {
                System.out.println(u0.toString());
            }

            // Основная работа сервера чата
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
            System.out.println("Сервер чата завершил свою работу");
        }
    }
}
