package com.airbnb.service;

import com.airbnb.dto.LocationDto;
import com.airbnb.entity.Location;
import jakarta.transaction.Transactional;

import java.util.List;

public interface LocationService {



    public void addLocation(LocationDto locationDto);


    @Transactional
    public void deleteLocation(String locationaName);


    public List<Location> getAllLocation();
}
