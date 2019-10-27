package com.caradverts.carAdverts.service;

import com.caradverts.carAdverts.Fuel;
import com.caradverts.carAdverts.entity.Car;
import com.caradverts.carAdverts.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        Car myCar = car.orElseThrow(()-> new CarNotFoundException("CAR NOT Found Error!...please try again with another car"));
        return myCar;
    }

    @Override
    public void saveCar(Car car) {
        this.validateInputFields(car);

         carRepository.save(car);

    }

    @Override
    public void deleteCarById(long id) {

        Optional<Car> car = carRepository.findById(id);

        if(!car.isPresent()){
            throw new CarNotFoundException("CAR NOT Found Error!, Car is already deleted!");
        }
        carRepository.deleteById(id);

    }

    @Override
    public List<Car> retrieveAllCarsSorted(String field) {
        List<Car> sortedCars = (List<Car>) carRepository.findAll(Sort.by(field));
        return sortedCars;
    }

    private void validateInputFields(Car car){
        boolean newCar = car.isNewCar();
        int mileage = car.getMileage();
        Optional<Date> firstRegistration = Optional.ofNullable(car.getFirstRegistration());
        Optional<String> title = Optional.ofNullable(car.getTitle());
        Optional<Fuel> fuel = Optional.ofNullable(car.getFuel());
        Optional<String> price = Optional.ofNullable(car.getPrice());
        if(!title.isPresent() || !fuel.isPresent() || !price.isPresent() ){
            throw new ValidationErrorInputFieldsException("VALIDATION ERROR!, please enter all required fields ");
        }
        if(!newCar &&(mileage == 0 || !firstRegistration.isPresent())){
            throw new ValidationErrorInputFieldsException("VALIDATION ERROR!, mileage and firstRegistration are required fields  ");
        }


    }


}
