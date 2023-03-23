package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: Egor Bekhterev
 * @date: 22.03.2023
 * @project: job4j_cars
 */
@Data
@AllArgsConstructor
public class CarDto {
    private String model;
    private int brandId;
    private int bodyId;
    private int engineId;
}
