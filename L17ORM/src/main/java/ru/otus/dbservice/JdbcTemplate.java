package ru.otus.dbservice;

import ru.otus.executor.DbExecutor;
import ru.otus.executor.DbExecutorImpl;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.sql.Connection;
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
    public void create(T objectData) {
        if (checkAnnotation(objectData)) {


            try (Connection connection = dataSource.getConnection()) {
                DbExecutor<T> executor = new DbExecutorImpl<>(connection);
                Class clazz = objectData.getClass();

                StringBuilder sqlString = new StringBuilder();
                sqlString.append("create table " + getTableName(clazz) + "(");
                for (Field f : clazz.getDeclaredFields()) {
                    sqlString.append(f.getName() + " " + getDBType(f.getType().getTypeName()) + ", ");
                }
                int length = sqlString.length();

                sqlString.delete(length - 2, length).append(")");

                System.out.println(sqlString);
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
            int Id = 1;

          executor.selectRecord("select * from " + getTableName(clazz) + " where id = ?", Id,
                resultSet -> {

                    try {

                        if (resultSet.next()) {

                            List<String> params = new ArrayList<>();

                            Constructor constructor = clazz.getDeclaredConstructor();

                            Parameter[] parameters = constructor.getParameters();
                            for(Parameter parameter: parameters)
                            {
                                System.out.println( parameter.getName() + "  " +   parameter.getType());

                                //parameter.getName()
                            }


                            Class<T> o = (Class<T>) constructor.newInstance(params);
                            Class<T> o1 = o;

                            //return new User(resultSet.getLong("id"), resultSet.getString("name"));
                            //  TestExample o1 = (TestExample) constructor.newInstance();
                            // System.out.printf("");
                            return null;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            );


            return null;
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

    private boolean checkAnnotation(Object o) {

        Class clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field f : declaredFields) {
            if (Arrays.stream(f.getDeclaredAnnotations()).filter(a ->
                a.toString().substring(a.toString().lastIndexOf(".") + 1).replace("(", "").replace(")", "")
                    .equals("Id")).findFirst().isPresent()
            ) {
                return true;
            }
        }
        return false;
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
