package com.airbnb.service;

import com.airbnb.dto.PaymentResponseDto;
import com.airbnb.dto.PropertyPackageBookingDto;
import com.airbnb.entity.PropertyUser;

public interface PropertyPackageBookingService {


   public String addPackageBooking(PropertyPackageBookingDto propertyPackageBookingDto , PropertyUser propertyUser);
}
