package config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Класс-конфигурация Hibernate для создания фабрики сессий.
 * Фабрика сессий отвечает за создание сессий и установку ORM-соединения.
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
public class HibernateTestConfiguration {

    /**
     * Создание стандартного реестра сервисов, используя файл конфигурации.
     */
    private final static StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure().build();

    /**
     * MetadataSource создает метаданные, включая объектно-реляционное отображение (ORM) сущностей.
     * @return Объект Hibernate SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
            return new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();
    }
}
