package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@Data
@Table(name = "body_type")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BodyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;
}
