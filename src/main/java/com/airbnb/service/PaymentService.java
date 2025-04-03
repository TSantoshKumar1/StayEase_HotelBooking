package com.airbnb.service;

import com.airbnb.dto.PaymentResponseDto;
import com.airbnb.entity.Payment;
import com.airbnb.entity.PropertyUser;

public interface PaymentService {

    public String addPayment(int amount, String currency ,long bookingId , PropertyUser propertyUser);

    public PaymentResponseDto paymentVerify(String paymentId , String orderId, String signature);
}
