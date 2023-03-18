package config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
public class HibernateTestConfiguration {

    private final static StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure().build();

    public static SessionFactory getSessionFactory() {
            return new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();
    }
}
