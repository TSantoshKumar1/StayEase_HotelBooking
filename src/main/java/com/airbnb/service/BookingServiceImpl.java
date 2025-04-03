package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private BucketService bucketService;

    public BookingServiceImpl(PropertyRepository propertyRepository, BookingRepository bookingRepository, BucketService bucketService) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.bucketService = bucketService;
    }


    @Override
    public Booking createBooking(Property property, BookingDto bookingDto, PropertyUser propertyUser) {


        Booking booking = new Booking();
        booking.setGuestName(bookingDto.getGuestName());

        Integer nightlyPrice = property.getNightlyPrice();
        Integer totalNights = bookingDto.getTotalNights();

        booking.setTotalNights(totalNights);

        Integer totalPrice = nightlyPrice * totalNights;
        booking.setTotalPrice(totalPrice);

        booking.setCheck_In(bookingDto.getCheck_In());
        booking.setCheck_Out(bookingDto.getCheck_Out());

        booking.setProperty(property);
        booking.setPropertyUser(propertyUser);

        Booking savedBooking = bookingRepository.save(booking);

        return savedBooking;
    }


    @Transactional
    @Override
    public void deleteBooking(Property property) {

        bookingRepository.deleteByPropertyId(property.getId());



    }

}