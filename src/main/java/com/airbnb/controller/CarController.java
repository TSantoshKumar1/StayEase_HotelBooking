package com.airbnb.controller;

import com.airbnb.dto.CarDto;
import com.airbnb.entity.Car;
import com.airbnb.entity.CarBooking;
import com.airbnb.repository.CarRepository;
import com.airbnb.service.CarBookingService;
import com.airbnb.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private CarRepository carRepository;
    private CarService carService;
    private CarBookingService carBookingService;


    public CarController(CarRepository carRepository, CarService carService, CarBookingService carBookingService) {
        this.carRepository = carRepository;
        this.carService = carService;
        this.carBookingService = carBookingService;
    }


    @PostMapping("/addCar")
    public ResponseEntity<String> addCar(@RequestBody CarDto carDto) {

        carService.addCar(carDto);

        return new ResponseEntity<>("car added successfully", HttpStatus.CREATED);
    }


    @GetMapping("/getCars")
    public ResponseEntity<List<Car>> getAllCar() {

        List<Car> allCar = carService.getAllCar();

        return new ResponseEntity<>(allCar, HttpStatus.OK);

    }


    @GetMapping("/getCar/{carId}")
    public ResponseEntity<?> getCar(@PathVariable Long carId) {

        Car car = carService.getCar(carId);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>("resource not found ", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("/deleteCar/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable long carId) {

        if (carRepository.existsById(carId)) {
            carService.deleteCar(carId);
            return new ResponseEntity<>("deleted successfull", HttpStatus.OK);
        }

        return new ResponseEntity<>("resource not found ", HttpStatus.CONFLICT);

    }

    @PutMapping("/updateCar/{carId}")
    public ResponseEntity<?>updateCar(@PathVariable long carId,@RequestBody CarDto carDto ){

        if (carRepository.existsById(carId)) {

            carService.updateCar(carId,carDto);
            return new ResponseEntity<>("updated successfull", HttpStatus.OK);
        }

        return new ResponseEntity<>("resource not found ", HttpStatus.CONFLICT);

    }


    @PostMapping("/carBooking")
    public ResponseEntity<?> carBooking(@RequestBody CarBooking carBooking){

        carBookingService.carBooking(carBooking);


        return new ResponseEntity<>("carbooking succesfully",HttpStatus.OK);
    }





}