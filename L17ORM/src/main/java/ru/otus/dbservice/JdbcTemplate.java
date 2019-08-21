package ru.otus.dbservice;

import ru.otus.domain.User;
import ru.otus.executor.DbExecutor;
import ru.otus.executor.DbExecutorImpl;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class JdbcTemplate<T> implements DBService<T> {

    private final DataSource dataSource;
    Map<String, String> types = new HashMap<String, String>();

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        types.put("String", "varchar(255)");
        types.put("int", "int");
        types.put("long", "bigint");
    }

    @Override
    public void create(Class clazz) {
        if (checkAnnotation(clazz)) {


            try (Connection connection = dataSource.getConnection()) {
                DbExecutor<T> executor = new DbExecutorImpl<>(connection);
                // Class clazz = objectData.getClass();

                StringBuilder sqlString = new StringBuilder();
                sqlString.append("create table " + getTableName(clazz) + "(");
                for (Field f : clazz.getDeclaredFields()) {
                    sqlString.append(f.getName() + " " + getDBType(f.getType().getTypeName()) + ", ");
                }
                int length = sqlString.length();

                sqlString.delete(length - 2, length).append(")");

                executor.insertRecord(sqlString.toString(), null);

            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

        } else {
            throw new RuntimeException("Not DB object. Please mark primary key field with annotation @Id");
        }

    }

    @Override
    public void update(T objectData) {

        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);
            Class clazz = objectData.getClass();

            StringBuilder fieldsString = new StringBuilder();
            StringBuilder valueString = new StringBuilder();

            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                fieldsString.append(f.getName() + ", ");
                if (getDBType(f.getType().getTypeName()).equals("varchar(255)")) {
                    valueString.append("'");
                    valueString.append(f.get(objectData));
                    valueString.append("'");
                } else {
                    valueString.append(f.get(objectData));
                }
                valueString.append(", ");
            }
            int lengthF = fieldsString.length();

            fieldsString.delete(lengthF - 2, lengthF);
            int lengthV = valueString.length();

            fieldsString.delete(lengthF - 2, lengthF);
            valueString.delete(lengthV - 2, lengthV);

            executor.insertRecord("insert into " + getTableName(clazz) + " (" + fieldsString + ")  values(" + valueString + ")", null);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }


    }

    @Override
    public void createOrUpdate(Object objectData) {

    }

    @Override
    public T load(long id, Class clazz) {

        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);

            Optional<T> t = executor.selectRecord(id, getTableName(clazz), getAnnotationFieldName(clazz),
                    resultSet -> {
                        try {
                            if (resultSet.next()) {
                                List<Object> params = new ArrayList<>();
                                Constructor constructor = null;
                                for (Constructor<T> c : clazz.getDeclaredConstructors()) {
                                    constructor = c;
                                    constructor.setAccessible(true);
                                }
                                Parameter[] parameters = constructor.getParameters();
                                for (Parameter parameter : parameters) {

                                    if (parameter.getType().getName().equals("int")) {
                                        params.add(resultSet.getInt(parameter.getName()));
                                    } else if (parameter.getType().getName().equals("long")) {
                                        params.add(resultSet.getLong(parameter.getName()));
                                    } else {
                                        params.add(resultSet.getString(parameter.getName()));
                                    }
                                }
                                T o = (T) constructor.newInstance(params.toArray());
                                return (T) o;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
            );
            return t.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private String getDBType(String type) {
        return types.getOrDefault(getClassName(type), "varchar(255)");
    }


    private String getTableName(Class clazz) {

        String fullClassName = clazz.getName();
        return getClassName(fullClassName);
    }

    private String getClassName(String fullClassName) {
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }


    private boolean checkAnnotation(Class clazz) {
        if (getAnnotationFieldName(clazz) != "") {
            return true;
        }
        return false;
    }

    private String getAnnotationFieldName(Class clazz) {


        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field f : declaredFields) {

            if (Arrays.stream(f.getDeclaredAnnotations()).filter(a ->
                    a.toString().substring(a.toString().lastIndexOf(".") + 1).replace("(", "").replace(")", "")
                            .equals("Id")).findFirst().isPresent()
            ) {

                return f.getName();
            }
        }
        return "";
    }

}


//@Override
//public long saveUsers(User user) {
//    try (Connection connection = dataSource.getConnection()) {
//    DbExecutor<User> executor = new DbExecutorImpl<>(connection);
//    long userId = executor.insertRecord("insert into user(name) values (?)", Collections.singletonList(user.getName()));
//    connection.commit();
//    System.out.println("created user:" + userId);
//    return userId;
//    } catch (Exception ex) {
//    ex.printStackTrace();
//    throw new RuntimeException(ex);
//    }
//    }
//
//@Override
//public Optional<User> getUser(long id) {
//    try (Connection connection = dataSource.getConnection()) {
//    DbExecutor<User> executor = new DbExecutorImpl<>(connection);
//    Optional<User> user = executor.selectRecord("select id, name from user where id  = ?", id, resultSet -> {
//    try {
//    if (resultSet.next()) {
//    return new User(resultSet.getLong("id"), resultSet.getString("name"));
//    }
//    } catch (SQLException e) {
//    e.printStackTrace();
//    }
//    return null;
//    });
//    System.out.println("user:" + user);
//    return user;
//    } catch (Exception ex) {
//    ex.printStackTrace();
//    throw new RuntimeException(ex);
//    }
//    }
