package com.caradverts.carAdverts.service;

import com.caradverts.carAdverts.Fuel;
import com.caradverts.carAdverts.entity.Car;
import com.caradverts.carAdverts.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> retrieveAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public Car retrieveCar(long id) {
        Optional<Car> car = carRepository.findById(id);
        Car myCar = car.orElseThrow(() -> new CarNotFoundException("CAR NOT Found Error!...please try again with another car"));
        return myCar;
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteCarById(long id) {

        Optional<Car> car = carRepository.findById(id);

        if (!car.isPresent()) {
            throw new CarNotFoundException("CAR NOT Found Error!, Car is already deleted!");
        }
        carRepository.deleteById(id);

    }

    @Override
    public List<Car> retrieveAllCarsSorted(String field) {
        List<Car> sortedCars = (List<Car>) carRepository.findAll(Sort.by(field));
        return sortedCars;
    }

    public List<String> validateInputFields(Car car) {
        boolean newCar = car.isNewCar();
        List<String> validationErrors = new ArrayList<>();
        int mileage = car.getMileage();
        Optional<Date> firstRegistration = Optional.ofNullable(car.getFirstRegistration());
        Optional<String> title = Optional.ofNullable(car.getTitle());
        Optional<Fuel> fuel = Optional.ofNullable(car.getFuel());
        Optional<String> price = Optional.ofNullable(car.getPrice());
        if (!title.isPresent()) {
            validationErrors.add("title field is requiered ");
        }
        if (!price.isPresent()) {
            validationErrors.add("fuel is requiered field");
        }
        if (!fuel.isPresent() || !price.isPresent()) {
            validationErrors.add("fuel is requiered field");
        }
        if (!newCar && mileage == 0) {
            validationErrors.add("mileage is required field");
        }
        if (!newCar && !firstRegistration.isPresent()) {
            validationErrors.add("firstRegistration is required field");
        }

        return validationErrors;


    }


}
