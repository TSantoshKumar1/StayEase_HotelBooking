package com.airbnb.service;

import com.airbnb.dto.CarDto;
import com.airbnb.entity.Car;
import com.airbnb.entity.Property;
import com.airbnb.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CarServiceImp implements CarService{

    private CarRepository carRepository;

    public CarServiceImp(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void addCar(CarDto carDto) {

        Car car = new Car();
        car.setModel(carDto.getModel());
        car.setName(carDto.getName());
        car.setIsAvailable(carDto.getAvailable());
        car.setPricePerKm(carDto.getPricePerKm());
        carRepository.save(car);
    }

    @Override
    public List<Car> getAllCar() {
      List<Car> allCars = carRepository.findAll();
      return allCars;
    }

    @Override
    public Car getCar(Long carId) {

        Car car = carRepository.findById(carId).get();
        return car;
    }

    @Override
    public void deleteCar(Long carId) {

        carRepository.deleteById(carId);

    }

    @Override
    public void updateCar(long carId, CarDto carDto) {
        Car car = carRepository.findById(carId).get();


        if(carDto.getName()!=null){
            car.setName(carDto.getName());

        }

        if(carDto.getModel()!=null){

            car.setModel(carDto.getModel());
        }
        if(carDto.getAvailable()!=null) {

            car.setIsAvailable(carDto.getAvailable());
        }

        if(carDto.getPricePerKm()!=null){

            car.setPricePerKm(carDto.getPricePerKm());

        }

        carRepository.save(car);

    }



}






