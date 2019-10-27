package com.caradverts.carAdverts.service;

import com.caradverts.carAdverts.entity.Car;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
    List<Car> retrieveAllCars();

    Car retrieveCar(long id);

    Car saveCar(Car car);

    void deleteCarById(long id);

    List<Car> retrieveAllCarsSorted(String field);

    List<String> validateInputFields(Car car);

}
