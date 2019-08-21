package ru.otus.dbservice;


public interface DBService<T> {

    void create(Class clazz);

    void update(T objectData);

    void createOrUpdate(T objectData);

    T load(long id, Class<T> clazz);

}