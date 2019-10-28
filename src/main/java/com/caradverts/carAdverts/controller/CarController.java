package com.caradverts.carAdverts.controller;

import com.caradverts.carAdverts.entity.Car;
import com.caradverts.carAdverts.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CarController {


    private CarService carService;

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.retrieveAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarByID(@PathVariable Long id) {
        Car car = carService.retrieveCar(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }


    @PutMapping("/car/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @Valid @RequestBody Car car) {

        //Set the id of the passed car object to the passed `id`
        car.setId(id);

        //To Do:should be moved to service layer
        List<String> validationErrors = carService.validateInputFields(car);

        if (validationErrors.size() == 0) {
            Car res = carService.saveCar(car);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            List<String> res = validationErrors;
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/addcar")
    public ResponseEntity<?> addCar(@Valid @RequestBody Car car) {

        //To Do:should be moved to service layer
        List<String> validationErrors = carService.validateInputFields(car);

        if (validationErrors.size() == 0) {
            Car res = carService.saveCar(car);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            List<String> res = validationErrors;
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {

        carService.deleteCarById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sortedcars/{sortedBy}")
    public ResponseEntity<List<Car>> getAllSortedCars(@PathVariable String sortedBy) {
        List<Car> cars = carService.retrieveAllCarsSorted(sortedBy);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


}
