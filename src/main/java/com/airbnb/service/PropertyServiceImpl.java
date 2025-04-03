package com.airbnb.service;

import com.airbnb.dto.PropertyDto;
import com.airbnb.entity.Country;
import com.airbnb.entity.Location;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.CountryRepository;
import com.airbnb.repository.LocationRepository;
import com.airbnb.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private LocationRepository locationRepository;
    private CountryRepository countryRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, LocationRepository locationRepository, CountryRepository countryRepository) {
        this.propertyRepository = propertyRepository;
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
    }


    @Transactional
    @Override
    public void addProperty(PropertyDto propertyDto ) {

        Property property = new Property();
        property.setPropertyName(propertyDto.getPropertyName());
        property.setBathroom(propertyDto.getBathroom());
        property.setBedroom(propertyDto.getBedroom());
        property.setGuest(propertyDto.getGuest());
       property.setNightlyPrice(propertyDto.getNightlyPrice());


        Country country = propertyDto.getCountry();
        Location location = propertyDto.getLocation();

        property.setCountry(country);
        property.setLocation(location);


        propertyRepository.save(property);


    }

    @Transactional
    @Override
    public void deletePropertyById(Long propertyId) {

        propertyRepository.deleteById(propertyId);
    }



    @Transactional
    @Override
    public void deletePropertyBylocationOrCountry(String locationName) {

        propertyRepository.deleteAllPropertyBylocationOrCountry(locationName);

    }



    @Transactional
    @Override
    public Property updateProperty(Long propertyId, PropertyDto propertyDto) {


        Property property = propertyRepository.findById(propertyId).get();


        if(propertyDto.getPropertyName()!=null){

            property.setPropertyName(propertyDto.getPropertyName());
        }

        if(propertyDto.getBathroom()!=null){

            property.setBathroom(propertyDto.getBathroom());
        }
        if(propertyDto.getBedroom()!=null) {

            property.setBedroom(propertyDto.getBedroom());
        }
        if(propertyDto.getGuest()!=null){

            property.setGuest(propertyDto.getGuest());

        }
        if(propertyDto.getNightlyPrice()!=null){

            property.setNightlyPrice(propertyDto.getNightlyPrice());
        }

        if(propertyDto.getCountry()!=null){

            property.setLocation(propertyDto.getLocation());
        }
        if(propertyDto.getLocation()!=null){

            property.setCountry(propertyDto.getCountry());
        }


        Property updateProperty = propertyRepository.save(property);
        return updateProperty;


    }


    @Transactional
    @Override
    public Property updateProperty(String propertyName, PropertyDto propertyDto) {

        Property property = propertyRepository.findByPropertyName(propertyName);


        if(propertyDto.getPropertyName()!=null){

            property.setPropertyName(propertyDto.getPropertyName());
        }

        if(propertyDto.getBathroom()!=null){

            property.setBathroom(propertyDto.getBathroom());
        }
        if(propertyDto.getBedroom()!=null) {

            property.setBedroom(propertyDto.getBedroom());
        }
        if(propertyDto.getGuest()!=null){

            property.setGuest(propertyDto.getGuest());

        }
        if(propertyDto.getNightlyPrice()!=null){

            property.setNightlyPrice(propertyDto.getNightlyPrice());
        }

        if(propertyDto.getCountry()!=null){

            property.setLocation(propertyDto.getLocation());
        }
        if(propertyDto.getLocation()!=null){

            property.setCountry(propertyDto.getCountry());
        }


        Property updateProperty = propertyRepository.save(property);

        return updateProperty;

    }
}
