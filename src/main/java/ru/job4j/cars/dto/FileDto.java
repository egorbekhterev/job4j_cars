package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: Egor Bekhterev
 * @date: 21.03.2023
 * @project: job4j_cars
 */
@Data
@AllArgsConstructor
public class FileDto {

    private String name;

    private byte[] content;
}
