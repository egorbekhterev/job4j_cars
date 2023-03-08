package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: Egor Bekhterev
 * @date: 08.03.2023
 * @project: job4j_cars
 */
@Entity
@Table(name = "auto_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
}
