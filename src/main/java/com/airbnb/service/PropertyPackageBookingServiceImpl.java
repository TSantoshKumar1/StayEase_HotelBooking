package com.airbnb.service;


import com.airbnb.dto.PropertyPackageBookingDto;
import com.airbnb.entity.PropertyPackage;
import com.airbnb.entity.PropertyPackageBooking;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyPackageBookingRepository;
import com.airbnb.repository.PropertyPackageRepository;
import org.springframework.stereotype.Service;

@Service
public class PropertyPackageBookingServiceImpl implements PropertyPackageBookingService{


    private PropertyPackageBookingRepository propertyPackageBookingRepository;
    private final PropertyPackageRepository propertyPackageRepository;
    private PaymentService paymentService;





    public PropertyPackageBookingServiceImpl(PropertyPackageBookingRepository propertyPackageBookingRepository,
                                             PropertyPackageRepository propertyPackageRepository, PaymentService paymentService

                                             ) {

        this.propertyPackageBookingRepository = propertyPackageBookingRepository;
        this.propertyPackageRepository = propertyPackageRepository;
        this.paymentService = paymentService;
    }

    @Override
    public String addPackageBooking(PropertyPackageBookingDto propertyPackageBookingDto, PropertyUser propertyUser) {

        PropertyPackageBooking propertyPackageBooking = new PropertyPackageBooking();

        propertyPackageBooking.setId(propertyPackageBookingDto.getId());
        propertyPackageBooking.setBookingDate(propertyPackageBookingDto.getBookingDate());
        Long id = propertyPackageBooking.getPropertyUser().getId();
        PropertyPackage propertyPackage1 = propertyPackageRepository.findById(id).get();

       propertyPackageBooking.setPropertyPackage(propertyPackage1);

        propertyPackageBooking.setPropertyUser(propertyUser);

        int totalPrice = propertyPackageBookingDto.getTotalPrice().intValue();

        String orderId = paymentService.addPayment(totalPrice, "Rupees", propertyPackageBookingDto.getId(), propertyUser);

        propertyPackageBooking.setTotalPrice(propertyPackageBookingDto.getTotalPrice());
        propertyPackageBookingRepository.save(propertyPackageBooking);

        return orderId;
    }
}
