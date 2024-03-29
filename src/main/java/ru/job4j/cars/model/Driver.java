package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: Egor Bekhterev
 * @date: 16.03.2023
 * @project: job4j_cars
 */
@Data
@Table(name = "driver")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
