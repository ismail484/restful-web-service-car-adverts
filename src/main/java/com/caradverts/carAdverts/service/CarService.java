package com.caradverts.carAdverts.service;

import com.caradverts.carAdverts.entity.Car;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarService {
     List<Car> retrieveAllCars();
     Car retrieveCar(long id);
     void saveCar(Car car);
     void deleteCarById(long id);
     List<Car> retrieveAllCarsSorted(String field);

}
