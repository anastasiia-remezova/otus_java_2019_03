package dbservice;


public interface DBService<T> {

    void create(T objectData);

    void update(T objectData);

    void createTable(Class clazz);

    T load(long id, Class<T> clazz);

}