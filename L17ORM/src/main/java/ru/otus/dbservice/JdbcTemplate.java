package ru.otus.dbservice;

import org.javatuples.Pair;
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
    Map<Class, HashMap<String, String>> classFieldsTypes = new HashMap<Class, HashMap<String, String>>();

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        types.put("String", "varchar(255)");
        types.put("int", "int");
        types.put("long", "bigint");
    }

    @Override
    public void createTable(Class clazz) {
        if (checkAnnotation(clazz)) {
            try (Connection connection = dataSource.getConnection()) {
                DbExecutor<T> executor = new DbExecutorImpl<>(connection);

                StringBuilder sqlString = new StringBuilder();
                sqlString.append("create table " + getTableName(clazz) + "(");

                for (Map.Entry<String, String> entry : getFieldsTypes(clazz).entrySet()) {
                    sqlString.append(entry.getKey() + " " + getDBType(entry.getValue()) + ", ");
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

            StringBuilder sql = new StringBuilder();
            StringBuilder whereClause = new StringBuilder();
            Pair id = new Pair(getAnnotationFieldName(clazz), null);

            List params = new LinkedList();

            sql.append("update " + getTableName(clazz) + " set ");

            for (Map.Entry<String, String> entry : getFieldsTypes(clazz).entrySet()) {

                Field f = clazz.getDeclaredField(entry.getKey());
                f.setAccessible(true);

                if (!entry.getKey().equals(id.getValue0())) {
                    sql.append(" " + entry.getKey() + " = ?,");
                    params.add(f.get(objectData).toString());
                } else {
                    id = new Pair(getAnnotationFieldName(clazz), f.get(objectData).toString());
                }

            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where " + id.getValue0() + " = ? ");

            params.add(id.getValue1());

            executor.insertRecord(sql.toString(), params);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void create(T objectData) {

        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);
            Class clazz = objectData.getClass();

            StringBuilder fieldsString = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            List<String> valueList = new ArrayList<>();

            for (Map.Entry<String, String> entry : getFieldsTypes(clazz).entrySet()) {

                if (fieldsString.length() != 0) {
                    fieldsString.append(", ");
                }

                Field f = clazz.getDeclaredField(entry.getKey());
                f.setAccessible(true);
                valueList.add(f.get(objectData).toString());
                fieldsString.append(entry.getKey());

            }

            sql.append("insert into " + getTableName(clazz) + " (" + fieldsString + ")  values(");
            valueList.forEach(v -> sql.append("?,"));
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");

            executor.insertRecord(sql.toString(), valueList);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
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

    private Map<String, String> getFieldsTypes(Class clazz) {
        if (classFieldsTypes.containsKey(clazz)) {
            return classFieldsTypes.get(clazz);
        } else {
            HashMap<String, String> ft = new HashMap<>();
            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                ft.put(f.getName(), f.getType().getTypeName());
            }
            classFieldsTypes.put(clazz, ft);
            return ft;
        }
    }
}
