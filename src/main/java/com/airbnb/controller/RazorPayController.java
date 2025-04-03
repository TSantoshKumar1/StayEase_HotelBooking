package com.airbnb.controller;

import com.airbnb.dto.PaymentResponseDto;
import com.airbnb.entity.Payment;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class RazorPayController {

    private PaymentService paymentService;

    public RazorPayController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/addPayment/{amount}/{currency}/{bookingId}")
    public ResponseEntity<String>propertyOrderCreated(@PathVariable int amount, @PathVariable String currency,@PathVariable long bookingId , @AuthenticationPrincipal PropertyUser propertyUser){


        String razoreDetail = paymentService.addPayment(amount, currency,bookingId, propertyUser);
        return new ResponseEntity<>(razoreDetail, HttpStatus.OK);
    }

    
    @PostMapping("/verifyPayment/{orderId}/{paymentId}/{signature}")
    public ResponseEntity<?>verifyPayment(@PathVariable String orderId,String paymentId,String signature ){

        PaymentResponseDto paymentResponseDto = paymentService.paymentVerify(orderId, paymentId, signature);
        return new ResponseEntity<>(paymentResponseDto,HttpStatus.OK);
    }

}
