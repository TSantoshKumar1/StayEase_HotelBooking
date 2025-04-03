package com.airbnb.service;

import com.airbnb.dto.LocationDto;
import com.airbnb.entity.Location;
import com.airbnb.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationServiceImpl implements  LocationService{

    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public void addLocation(LocationDto locationDto) {

        Location location = new Location();
        location.setLocationName(locationDto.getLocationName());

        locationRepository.save(location);
    }



    @Transactional
    @Override
    public void deleteLocation(String locationaName) {

        locationRepository.deleteByLocationName(locationaName);
    }




    @Override
    public List<Location> getAllLocation() {

        List<Location> allLocation = locationRepository.findAll();
        return allLocation;
    }
}
