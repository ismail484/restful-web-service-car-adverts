package com.caradverts.carAdverts.controller;

import com.caradverts.carAdverts.Fuel;
import com.caradverts.carAdverts.entity.Car;
import com.caradverts.carAdverts.service.CarNotFoundException;
import com.caradverts.carAdverts.service.CarService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceUnitTest {

    @Autowired
    CarService carService;


    /**
     * Tests the retrieval of car list
     *
     * @throws Exception if the update operation of a service fails
     */

    @Test
    public void getCars() throws Exception {
        this.saveCustomCar();
        Assert.assertNotNull(carService.retrieveAllCars());
    }

    /**
     * Tests the retrieval of sorted cars as list
     *
     * @throws Exception if the update operation of a service fails
     */


    @Test
    public void getSortedCars() throws Exception {
        this.saveCustomCar();
        List<Car> carList = carService.retrieveAllCarsSorted("mileage");
        Assertions.assertNotNull(carList);

    }

    /**
     * Tests the retrieval of a single car by ID.
     *
     * @throws Exception if the update operation of a service fails
     */
    @Test
    public void getCar() throws Exception {
        Car savedCar = this.saveCustomCar();
        Car carDatabase = carService.retrieveCar(savedCar.getId());

        assertEquals(carDatabase.getPrice(), savedCar.getPrice());
        assertEquals(carDatabase.getFuel(), savedCar.getFuel());
        assertEquals(carDatabase.getTitle(), savedCar.getTitle());
        assertEquals(carDatabase.isNewCar(), savedCar.isNewCar());


    }

    /**
     * Tests the retrieval of a single car by ID.
     *
     * @throws Exception if the update operation of a service fails
     */
    @Test(expected = CarNotFoundException.class)
    public void getCarWithProblems() throws Exception {

        carService.retrieveCar((long) 10);
    }

    @Test(expected = CarNotFoundException.class)
    public void deleteCarWithProblems() throws CarNotFoundException {

        carService.retrieveCar((long) 10);

        carService.deleteCarById((long) 10);
    }


    @Test
    public void addCar() throws Exception {

        Car car = new Car();
        car.setTitle("WV");
        car.setPrice("750000$");
        car.setFuel(Fuel.DIESEL);
        car.setNewCar(true);
        Car carSaved = carService.saveCar(car);
        long id = carSaved.getId();

        Car carFromDataBase = carService.retrieveCar(id);

        assertEquals(carFromDataBase.getPrice(), car.getPrice());
        assertEquals(carFromDataBase.getFuel(), car.getFuel());
        assertEquals(carFromDataBase.getTitle(), car.getTitle());
        assertEquals(carFromDataBase.isNewCar(), car.isNewCar());
    }

    @Test
    public void updateCar() throws Exception {
        Car savedCar = this.saveCustomCar();

        savedCar.setPrice("50$");
        carService.saveCar(savedCar);

        Car retrievedCar = carService.retrieveCar(savedCar.getId());

        assertEquals(savedCar.getPrice(), retrievedCar.getPrice());

        this.deleteLastCar(savedCar.getId());
    }

    /**
     * Tests the retrieval of a single car by ID.
     *
     * @throws Exception if the update operation of a service fails
     */
    @Test(expected = CarNotFoundException.class)
    public void deleteCar() throws Exception {
        Car savedCar = this.saveCustomCar();
        carService.deleteCarById(savedCar.getId());
        carService.retrieveCar(savedCar.getId());

    }


    /**
     * Tests the functionality of validationInputFields
     *
     * validationErrors will be NotNull
     */
    @Test()
    public void validationInputFields() throws Exception {
        Car car = getCarWithErrors() ;
        List<String> validationErrors = carService.validateInputFields(car);
        assertThat(validationErrors, hasSize(2));


    }

    private void deleteLastCar(long id) {
        carService.deleteCarById(id);
    }

    private Car saveCustomCar() {
        Car car = new Car();
        car.setTitle("Toyota");
        car.setPrice("300000$");
        car.setFuel(Fuel.DIESEL);
        car.setNewCar(true);
        return carService.saveCar(car);
    }

    private Car getCarWithErrors() {
        Car car = new Car();
        car.setTitle("Toyota");
        car.setPrice("300000$");
        car.setFuel(Fuel.DIESEL);
        car.setNewCar(false);
        return car;
    }
}
