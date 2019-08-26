package dbservice;

import domain.AddressDataSet;
import domain.PhoneDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

public class DBServiceImpl<T> implements DBService<T> {
    DataSource dataSource;
    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
    private SessionFactory sessionFactory;
    private StandardServiceRegistry serviceRegistry;

    public DBServiceImpl() {
        this.dataSource = dataSource;


        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();


    }

    @Override
    public void create(T objectData) {

        Class clazz = objectData.getClass();

        MetadataSources metasources = new MetadataSources(serviceRegistry);
        metasources.addAnnotatedClass(clazz);
        getRelatedClasses(clazz);
        metasources.addAnnotatedClass(PhoneDataSet.class);
        metasources.addAnnotatedClass(AddressDataSet.class);


       Metadata metadata = metasources.getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(objectData);
            session.getTransaction().commit();
        }

    }

    @Override
    public void update(T objectData) {


    }

    @Override
    public void createTable(Class clazz) {
    }

    @Override
    public T load(long id, Class<T> clazz) {
        return null;

    }

    Set<Class> getRelatedClasses(Class clazz){
        System.out.println("**************");
        for (Field declaredField : clazz.getDeclaredFields()) {
            System.out.println("declaredField: " + declaredField);
            //declaredField.setAccessible(true);
            for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations()) {
                System.out.println("declaredAnnotation: " + declaredAnnotation.annotationType());
//                for (Annotation field : declaredAnnotation) {
//                    System.out.println(field.getName());
//                }

            }

        }
        System.out.println("**************");
//        for(  Annotation a :clazz.getDeclaredAnnotations())
//      {
//          System.out.println("**************");
//          System.out.println(a.toString());
//          for (Field declaredField : a.annotationType().getDeclaredFields()) {
//              System.out.println(declaredField.getName());
//          }
//          System.out.println("**************");
//      }

        return null;

    }

}
