package com.airbnb.repository;

import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    public void deleteByPropertyId(Long propertyId);
}