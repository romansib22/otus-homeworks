package ru.romansib.otus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AbstractRepository<T> {
    private DataSource dataSource;
    private PreparedStatement psInsert;
    private PreparedStatement psFindById;
    private PreparedStatement psFindAll;
    private PreparedStatement psUpdate;
    private PreparedStatement psDelete;
    private List<Field> cachedFields;
    private Map<String,String> cachedFieldNames;
    private List<Field> pkFields;
    private final UserMapper mapper;
    private final Map<Field, Method> fieldGetMethodMap = new HashMap<>();
    private final Map<Field, Method> fieldSetMethodMap = new HashMap<>();

    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;
        fillCachedFields(cls);
        fillCachedFieldNames(cls);
        fillPkFields(cls);
        fillGetMethodMap(cls);
        fillSetMethodMap(cls);
        this.prepareInsert(cls);
        this.prepareFindById(cls);
        this.prepareFindAll(cls);
        this.prepareUpdate(cls);
        this.mapper = new UserMapper(cls);
        this.prepareDeleteById(cls);
    }

    public T findById(Object id) {
        try {
            psFindById.setObject(1, id);
            ResultSet rs = psFindById.executeQuery();
            return mapper.map(rs).get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ORMException("Ошибка запроса findById");
        }
    }

    public List<T> findAll() {
        try {
            ResultSet rs = psFindAll.executeQuery();
            return mapper.map(rs);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ORMException("Ошибка запроса findAll");
        }
    }

    public void save(T entity) {
        try {
            for (int i = 0; i < cachedFields.size(); i++) {
                psInsert.setObject(i + 1, fieldGetMethodMap.get(cachedFields.get(i)).invoke(entity));
            }
            psInsert.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Ошибка при сохранении пользователя: " + entity);
        }
    }

    public void update(T entity) {
        int k = 0;
        try {
            for (int i = 0; i < cachedFields.size(); i++) {
                psUpdate.setObject(i + 1, fieldGetMethodMap.get(cachedFields.get(i)).invoke(entity));
            }
            k = cachedFields.size();
            for (int i = 0; i < pkFields.size(); i++) {
                psUpdate.setObject(k + 1, fieldGetMethodMap.get(pkFields.get(i)).invoke(entity));
                k++;
            }
            psUpdate.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ORMException("Ошибка при обновлении пользователя: " + entity);
        }
    }

    public void deleteById(Object id) {
        try {
            psDelete.setObject(1, id);
            psDelete.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ORMException("Ошибка при удалении пользователя с id: " + id);
        }
    }

    private class UserMapper {
        Class<T> cls;

        public UserMapper(Class<T> cls) {
            this.cls = cls;
        }

        private List<T> map(ResultSet rs) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                T t = cls.getDeclaredConstructor().newInstance();
                for (Map.Entry<Field, Method> e : fieldSetMethodMap.entrySet()) {
                    String columnName = e.getKey().getName();
                    if (e.getKey().isAnnotationPresent(RepositoryField.class) && !Objects.equals(e.getKey().getAnnotation(RepositoryField.class).fieldName(), "")) {
                        columnName = e.getKey().getAnnotation(RepositoryField.class).fieldName();
                    }
                    if (e.getKey().getType().equals(String.class)) {
                        e.getValue().invoke(t, rs.getString(columnName));
                    } else if (e.getKey().getType().equals(Long.class)) {
                        e.getValue().invoke(t, rs.getLong(columnName));
                    } else if (e.getKey().getType().equals(Integer.class)) {
                        e.getValue().invoke(t, rs.getInt(columnName));
                        //etc
                    }

                }
                result.add(t);
            }
            return result;
        }
    }


    private void prepareInsert(Class cls) {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        String tableName = ((RepositoryTable) cls.getAnnotation(RepositoryTable.class)).title();
        StringBuilder query = new StringBuilder("insert into ");
        query.append(tableName).append(" (");
        // 'insert into users ('

        for (Field f : cachedFields) {
            query.append(cachedFieldNames.get(f.getName())).append(", ");
        }
        // 'insert into users (login, password, nickname, '
        query.setLength(query.length() - 2);
        query.append(") values (");
        // 'insert into users (login, password, nickname) values ('
        for (Field f : cachedFields) {
            query.append("?, ");
        }
        query.setLength(query.length() - 2);
        query.append(");");
        // 'insert into users (login, password, nickname) values (?, ?, ?);'
        try {
            psInsert = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }


    private void prepareFindById(Class cls) {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        String tableName = ((RepositoryTable) cls.getAnnotation(RepositoryTable.class)).title();

        StringBuilder query = new StringBuilder("select ");
        for (Field f : pkFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" ,");
        }
        for (Field f : cachedFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" ,");
        }
        query.setLength(query.length() - 2);
        query.append(" from ").append(tableName).append(" where ");

        for (Field f : pkFields) {
                query.append(f.getName()).append(" = ? ,");
        }
        query.setLength(query.length() - 2);
        query.append(";");
        try {
            psFindById = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    private void prepareFindAll(Class cls) {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        String tableName = ((RepositoryTable) cls.getAnnotation(RepositoryTable.class)).title();

        StringBuilder query = new StringBuilder("select ");
        for (Field f : pkFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" ,");
        }
        for (Field f : cachedFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" ,");
        }
        query.setLength(query.length() - 2);
        query.append(" from ").append(tableName);

        query.append(";");
        try {
            psFindAll = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }


    private void prepareUpdate(Class cls) {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        String tableName = ((RepositoryTable) cls.getAnnotation(RepositoryTable.class)).title();

        StringBuilder query = new StringBuilder("update ");
        query.append(tableName).append(" set ");
        for (Field f : cachedFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" = ? ,");
        }
        query.setLength(query.length() - 2);
        query.append(" where ");
        for (Field f : pkFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" = ? AND");
        }
        query.setLength(query.length() - 4);
        query.append(";");
        try {
            psUpdate = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    private void prepareDeleteById(Class cls) {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        String tableName = ((RepositoryTable) cls.getAnnotation(RepositoryTable.class)).title();

        StringBuilder query = new StringBuilder("delete from ");
        query.append(tableName).append(" where ");
        for (Field f : pkFields) {
            query.append(cachedFieldNames.get(f.getName())).append(" = ? AND");
        }
        query.setLength(query.length() - 4);
        query.append(";");
        try {
            psDelete = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    private void fillCachedFields(Class cls) {
        cachedFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> !f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());
    }

    private void fillCachedFieldNames(Class cls) {
        Arrays.stream(cls.getDeclaredFields()).forEach(f -> {
            if (f.isAnnotationPresent(RepositoryField.class)) {
                    cachedFieldNames.put(f.getName(), f.getAnnotation(RepositoryIdField.class).fieldName().isEmpty() ? f.getName() : f.getAnnotation(RepositoryIdField.class).fieldName());
            }
            if (f.isAnnotationPresent(RepositoryIdField.class)) {
                cachedFieldNames.put(f.getName(), f.getAnnotation(RepositoryIdField.class).fieldName().isEmpty() ? f.getName() : f.getAnnotation(RepositoryIdField.class).fieldName());
            }
        });
    }

    private void fillPkFields(Class cls) {
        pkFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());
    }

    private void fillGetMethodMap(Class cls) {
        for (Field f : cachedFields) {
            Method m = Arrays.stream(cls.getDeclaredMethods())
                    .filter(fl -> fl.getName().contains("get"))
                    .filter(fl -> (Character.toLowerCase(fl.getName().replace("get", "").charAt(0)) + (fl.getName().replace("get", "")).substring(1)).equals(f.getName()))
                    .findAny().get();
            fieldGetMethodMap.put(f, m);
        }
        for (Field f : pkFields) {
            Method m = Arrays.stream(cls.getDeclaredMethods())
                    .filter(fl -> fl.getName().contains("get"))
                    .filter(fl -> (Character.toLowerCase(fl.getName().replace("get", "").charAt(0)) + (fl.getName().replace("get", "")).substring(1)).equals(f.getName()))
                    .findAny().get();
            fieldGetMethodMap.put(f, m);
        }
    }

    private void fillSetMethodMap(Class cls) {
        List<Field> fields = Arrays.stream(cls.getDeclaredFields())
                .collect(Collectors.toList());
        for (Field f : fields) {
            Method m = Arrays.stream(cls.getDeclaredMethods())
                    .filter(fl -> fl.getName().contains("set"))
                    .filter(fl -> (Character.toLowerCase(fl.getName().replace("set", "").charAt(0)) + (fl.getName().replace("set", "")).substring(1)).equals(f.getName()))
                    .findAny().get();
            fieldSetMethodMap.put(f, m);
        }
    }
}
