package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;

public interface BookingService {

    public Booking createBooking( Property property ,  BookingDto bookingDto  , PropertyUser propertyUser);

    public void deleteBooking(Property property );
}
