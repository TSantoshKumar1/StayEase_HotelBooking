package com.airbnb.controller;


import com.airbnb.dto.LocationDto;
import com.airbnb.entity.Location;
import com.airbnb.repository.LocationRepository;
import com.airbnb.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationCountry {


    private LocationService locationService;
    private  LocationRepository locationRepository;

    public LocationCountry(LocationService locationService,
                           LocationRepository locationRepository) {
        this.locationService = locationService;
        this.locationRepository = locationRepository;
    }






    @PostMapping("/addLocation")
    public ResponseEntity<String> addLocation(@RequestBody LocationDto locationDto){


        locationService.addLocation(locationDto);

        return new ResponseEntity<>("location added successful ", HttpStatus.CREATED);


    }


    @DeleteMapping("/deleteLocation/{locationName}")
    public ResponseEntity<String>deleteLocation(@PathVariable String locationName) {

        boolean existLocation = locationRepository.existsByLocationName(locationName);
        if (existLocation) {
            locationService.deleteLocation(locationName);

            return new ResponseEntity<>("location deleted successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("locationName not found ", HttpStatus.CONFLICT);

    }



    @GetMapping("/fetchAllLocation")
    public ResponseEntity<List<Location>>fetchAllLocation(){

        List<Location> allLocation = locationService.getAllLocation();

        return new ResponseEntity<>(allLocation,HttpStatus.OK);

    }

}
