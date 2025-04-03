package com.airbnb.repository;

import com.airbnb.entity.PropertyPackageBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyPackageBookingRepository extends JpaRepository<PropertyPackageBooking, Long> {
}