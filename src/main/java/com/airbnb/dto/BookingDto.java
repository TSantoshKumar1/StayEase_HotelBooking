package com.airbnb.dto;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;

import java.time.LocalDateTime;

public class BookingDto {

    private Long id;

    private String guestName;

    private Integer totalNights;

    private Integer totalPrice;

    private LocalDateTime check_In;

    private LocalDateTime check_Out;

    private Property property;

    private PropertyUser propertyUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Integer getTotalNights() {
        return totalNights;
    }

    public void setTotalNights(Integer totalNights) {
        this.totalNights = totalNights;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCheck_In() {
        return check_In;
    }

    public void setCheck_In(LocalDateTime check_In) {
        this.check_In = check_In;
    }

    public LocalDateTime getCheck_Out() {
        return check_Out;
    }

    public void setCheck_Out(LocalDateTime check_Out) {
        this.check_Out = check_Out;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PropertyUser getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUser propertyUser) {
        this.propertyUser = propertyUser;
    }
}
