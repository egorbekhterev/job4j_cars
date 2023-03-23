package ru.job4j.cars.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс-конфигурация Hibernate для создания фабрики сессий.
 * Фабрика сессий отвечает за создание сессий и установку ORM-соединения.
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@Configuration
public class HibernateConfiguration {

    /**
     * MetadataSource создает метаданные, включая объектно-реляционное отображение (ORM) сущностей.
     * @return Объект Hibernate SessionFactory.
     */
    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
