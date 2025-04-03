package com.airbnb.controller;

import com.airbnb.dto.PaymentResponseDto;
import com.airbnb.dto.PropertyPackageBookingDto;
import com.airbnb.entity.PropertyPackage;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyPackageBookingRepository;
import com.airbnb.repository.PropertyPackageRepository;
import com.airbnb.service.PaymentService;
import com.airbnb.service.PropertyPackageBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PropertyPackageBooking")
public class PropertyPackageBookingController {

    private PropertyPackageBookingService propertyPackageBookingService;
    private PropertyPackageRepository propertyPackageRepository;
    private PaymentService paymentService;

    public PropertyPackageBookingController(PropertyPackageBookingService propertyPackageBookingService,
                                            PropertyPackageRepository propertyPackageRepository,
                                            PaymentService paymentService) {

        this.propertyPackageBookingService = propertyPackageBookingService;
        this.propertyPackageRepository = propertyPackageRepository;
        this.paymentService = paymentService;
    }

    @PostMapping("/packageOrder")
    public ResponseEntity<?> packageOrderCreated(@RequestBody PropertyPackageBookingDto propertyPackageBookingDto , @AuthenticationPrincipal PropertyUser propertyUser) {


        String packageName = propertyPackageBookingDto.getPropertyPackage().getPackageName();
        boolean exist = propertyPackageRepository.existsBypackageName(packageName);
        if(exist) {

            String orderId = propertyPackageBookingService.addPackageBooking(propertyPackageBookingDto, propertyUser);
            return new ResponseEntity<>(orderId, HttpStatus.OK);
        }

        return new ResponseEntity<>("package does not exist",HttpStatus.CONFLICT);
    }



    @PostMapping("/verifyPayment/{orderId}/{paymentId}/{signature}")
    public ResponseEntity<?>verifyPayment(@PathVariable String orderId,String paymentId,String signature ){

        PaymentResponseDto paymentResponseDto = paymentService.paymentVerify(orderId, paymentId, signature);
        return new ResponseEntity<>(paymentResponseDto,HttpStatus.OK);
    }
}


