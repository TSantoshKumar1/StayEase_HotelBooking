package com.airbnb.controller;


import com.airbnb.dto.PropertyDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private PropertyRepository propertyRepository;

    private PropertyService propertyService;


    public PropertyController(PropertyRepository propertyRepository, PropertyService propertyService) {
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
    }





    @PostMapping("/addProperty")
    public ResponseEntity<String>addProperty(@RequestBody PropertyDto propertyDto){


        String propertyName = propertyDto.getPropertyName();
        Long countryId = propertyDto.getCountry().getId();
        Long locationId = propertyDto.getLocation().getId();

        List<Property> properties = propertyRepository.findPropertyByPropertyNameAndLocatioinAndCountry(propertyName, countryId, locationId);
        if(properties != null && !properties.isEmpty()){
            return new ResponseEntity<>("this property is already added", HttpStatus.CONFLICT);
        }


        propertyService.addProperty(propertyDto );

        return new ResponseEntity<>("property added successful",HttpStatus.CREATED);

    }





//  fetch the property by location or country name..................
    @GetMapping("/location/{locationName}")
    public ResponseEntity<List<Property>> findProperty(@PathVariable String locationName){

        List<Property> propertyByLocation = propertyRepository.findPropertyByLocation(locationName);

       return new ResponseEntity<>(propertyByLocation, HttpStatus.OK);

    }


    @GetMapping("/{propertyName}")
    public ResponseEntity<?>findPropertyBypropertyName(@PathVariable String propertyName){

        if(propertyRepository.existsByPropertyName(propertyName)){

            Property property = propertyRepository.findByPropertyName(propertyName);
            return  new ResponseEntity<>(property,HttpStatus.OK);
        }

        return new ResponseEntity<>("property name does not exist",HttpStatus.OK);

    }



    @DeleteMapping("/deleteById/{propertyId}")
    public ResponseEntity<String> deletePropertyById(@PathVariable long propertyId) {

        Optional<Property> byId = propertyRepository.findById(propertyId);

        if (byId.isPresent()) {

        propertyService.deletePropertyById(propertyId);

        return new ResponseEntity<>("property deleted successful", HttpStatus.OK);
    }

    return new ResponseEntity<>("these property alredy deleted",HttpStatus.OK);
    }



    @DeleteMapping("/deleteByLocationOrCountry/{locationName}")
    public ResponseEntity<String> deletePropertiesBylocationAndCountry(@PathVariable String locationName){


        List<Property> propertyByLocationOrCountry = propertyRepository.findPropertyByLocation(locationName);

        if(propertyByLocationOrCountry.isEmpty()){

            return new ResponseEntity<>("these properties already deleted ",HttpStatus.OK);
}
            propertyService.deletePropertyBylocationOrCountry(locationName);

            return new ResponseEntity<>("properties are deleted ", HttpStatus.OK);

    }




    @PutMapping("/{propertyId}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long propertyId , @RequestBody  PropertyDto propertyDto){


        Property updateProperty = propertyService.updateProperty(propertyId, propertyDto);

        return new ResponseEntity<>(updateProperty , HttpStatus.OK);
    }



    @PutMapping("updatePropertyByPropertyName/{propertyName}")
    public ResponseEntity<Property> updateProperty(@PathVariable String propertyName , @RequestBody  PropertyDto propertyDto){


        Property updateProperty = propertyService.updateProperty(propertyName, propertyDto);

        return new ResponseEntity<>(updateProperty , HttpStatus.OK);
    }



}
