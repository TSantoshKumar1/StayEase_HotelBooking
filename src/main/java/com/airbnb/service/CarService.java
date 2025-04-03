package com.airbnb.service;

import com.airbnb.dto.CarDto;
import com.airbnb.entity.Car;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CarService {

public void addCar(CarDto carDto);

public List<Car> getAllCar();

public Car getCar(Long carId);

public void deleteCar(Long carId);
public void updateCar(long carId , CarDto carDto);
}
