package com.airbnb.dto;

import com.airbnb.entity.PropertyPackage;
import com.airbnb.entity.PropertyUser;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class PropertyPackageBookingDto {


   private Long Id;

    private LocalDate bookingDate;


    private Double totalPrice;


    private String status;


    private PropertyUser propertyUser;

    private PropertyPackage propertyPackage;

    public PropertyPackage getPropertyPackage() {
        return propertyPackage;
    }

    public void setPropertyPackage(PropertyPackage propertyPackage) {
        this.propertyPackage = propertyPackage;
    }

    public PropertyUser getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUser propertyUser) {
        this.propertyUser = propertyUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
