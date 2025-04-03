package com.airbnb.repository;

import com.airbnb.entity.CarBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {
}