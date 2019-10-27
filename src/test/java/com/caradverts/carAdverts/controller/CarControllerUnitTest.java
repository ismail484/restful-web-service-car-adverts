package com.caradverts.carAdverts.controller;

import com.caradverts.carAdverts.Fuel;
import com.caradverts.carAdverts.entity.Car;
import com.caradverts.carAdverts.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;


import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CarService carService;

    /**
     * Tests the retrieval of car list
     * @throws Exception if the update operation of a service fails
     */

    @Test
    public void getCars() throws Exception {
        mockMvc.perform(get("/cars/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(carService, times(1)).retrieveAllCars();
    }

    /**
     * Tests the retrieval of sorted cars as list
     * @throws Exception if the update operation of a service fails
     */

    @Test
    public void getSortedCars() throws Exception {
        mockMvc.perform(get("/sortedcars/mileage"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(carService, times(1)).retrieveAllCarsSorted("mileage");
    }

    /**
     * Tests the retrieval of a single car by ID.
     * @throws Exception if the update operation of a service fails
     */
    @Test
    public void getCar() throws Exception {
        mockMvc.perform(get("/car/1"))
                .andExpect(status().isOk());

        verify(carService, times(1)).retrieveCar(1);
    }


    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of the service fails
     */
    @Test
    public void deleteCar() throws Exception {

        mockMvc.perform(delete("/car/2"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).deleteCarById((long) 2);
    }

    /**
     * Tests the update of a single car by ID.
     * @throws Exception if the update operation of the service fails
     */
    @Test
    public void updateCar() throws Exception {
        Car car = getACar();
        car.setId((long) 3);
        mockMvc.perform(
                put("/car/3", car.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(car)))
                .andExpect(status().isOk());
    }

    /**
     * Tests the addition of a single car by ID.
     * @throws Exception if the update operation of the service fails
     */

    @Test
    public void addMyCar() throws Exception {
        Car car = getANewCar();
        mockMvc.perform(post("/addcar")
                .content(asJsonString(car))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    private Car getACar(){
        Car car = new Car();
        car.setTitle("BMW");
        car.setPrice("100000$");
        car.setFuel(Fuel.DIESEL);
        car.setNewCar(true);
        return car;
    }

    private Car getANewCar(){
        Car car = new Car();
        car.setTitle("Ferari");
        car.setPrice("100000$");
        car.setFuel(Fuel.GASOLINE);
        car.setNewCar(true);
        return car;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}