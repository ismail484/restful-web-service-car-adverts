package com.caradverts.carAdverts.controller;

import com.caradverts.carAdverts.Fuel;
import com.caradverts.carAdverts.entity.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class CarAdvertsApplicationTest {

    private static final String SERVER = "http://localhost:";
    private static final String GET_ALL_CARS_URL = "/cars";
    private static final String GET_CAR_ENDPOINT_URL = "/car/{id}";
    private static final String Add_CAR_ENDPOINT_URL = "/cars";
    private static final String UPDATE_CAR_ENDPOINT_URL = "/car/{id}";
    private static final String DELETE_CAR_ENDPOINT_URL = "/car/{id}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getCars() {
        ResponseEntity<List> response =
                this.restTemplate.getForEntity(SERVER + port + GET_ALL_CARS_URL, List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getCar() {
        Map< String, String > params = new HashMap< String, String >();
        params.put("id", "1");
        ResponseEntity<Car> response =
                this.restTemplate.getForEntity(SERVER + port +GET_CAR_ENDPOINT_URL, Car.class, params);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }


    @Test
    public void addCar() {
        Car car = getANewCar();
        Car response = this.restTemplate.postForObject(SERVER + port +Add_CAR_ENDPOINT_URL, car, Car.class);

        }

    @Test
    public void updateCar() {
        Map < String, String > params = new HashMap < String, String > ();
        params.put("id", "1");
        Car car = getACar();
         this.restTemplate.put(SERVER + port +UPDATE_CAR_ENDPOINT_URL, car, params);

    }

    @Test
    public void deleteCar() {
        Map < String, String > params = new HashMap < String, String > ();
        params.put("id", "1");
        this.restTemplate.delete(SERVER + port +DELETE_CAR_ENDPOINT_URL, params);

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
}
