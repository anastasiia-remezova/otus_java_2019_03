package dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
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
        try (Session session = getSessionFactory(objectData.getClass()).openSession()) {
            session.beginTransaction();
            session.save(objectData);
            session.getTransaction().commit();
        }

    }

    @Override
    public void update(T objectData) {
        try (Session session = getSessionFactory(objectData.getClass()).openSession()) {
            session.beginTransaction();
            session.save(objectData);
            session.getTransaction().commit();
        }

    }

    @Override
    public void createTable(Class clazz) {
    }

    @Override
    public T load(int id, Class<T> clazz) {
        try (Session session = getSessionFactory(clazz).openSession()) {
            return  session.get(clazz, id);
            //System.out.println(">>>>>>>>>>> selected:" + selected);
        }

    }

    public SessionFactory getSessionFactory(Class clazz) {

        //Class clazz = objectData.getClass();

        MetadataSources metasources = new MetadataSources(serviceRegistry);
        metasources.addAnnotatedClass(clazz);

        getRelatedClasses(clazz).forEach(relatedClass -> metasources.addAnnotatedClass(relatedClass));

        Metadata metadata = metasources.getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory;
    }

    Set<Class> getRelatedClasses(Class clazz) {
        Set<Class> classes = new HashSet<>();

        Set<Class> s = Set.of(OneToOne.class, ManyToOne.class, OneToMany.class, ManyToMany.class);

        for (Field declaredField : clazz.getDeclaredFields()) {
            Annotation a;
            for (Class c : s) {
                a = declaredField.getAnnotation(c);
                if (a != null) {
                    for (Method method : a.annotationType().getMethods()) {
                        if (method.getName().equals("targetEntity")) {
                            try {
                                classes.add((Class) method.invoke(a));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
        return classes;
    }
}
