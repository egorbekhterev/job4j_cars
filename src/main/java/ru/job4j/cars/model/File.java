package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@Data
@Table(name = "file")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String path;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }
}