package com.caradverts.carAdverts.repository;

import com.caradverts.carAdverts.entity.Car;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

}
