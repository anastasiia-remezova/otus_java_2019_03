package ru.otus.dbservice;


public interface DBService<T> {

    void create(T objectData);

    void update(T objectData);

    void createOrUpdate(T objectData);

    <T> T load(long id, Class<T> clazz);

}