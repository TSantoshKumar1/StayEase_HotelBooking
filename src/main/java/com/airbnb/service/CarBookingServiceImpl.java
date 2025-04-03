package com.airbnb.service;

import com.airbnb.entity.CarBooking;
import com.airbnb.repository.CarBookingRepository;
import org.springframework.stereotype.Service;

@Service
public class CarBookingServiceImpl implements CarBookingService{

    private CarBookingRepository carBookingRepository;
    private CarPricingService carPricingService;



    public CarBookingServiceImpl(CarBookingRepository carBookingRepository, CarPricingService carPricingService) {
        this.carBookingRepository = carBookingRepository;
        this.carPricingService = carPricingService;
    }




    @Override
    public void carBooking(CarBooking carBooking) {

        double price = carPricingService.calculatePrice(carBooking.getOrigin(), carBooking.getDestination());

        carBooking.setPrice(price);
        carBookingRepository.save(carBooking);

    }
}
