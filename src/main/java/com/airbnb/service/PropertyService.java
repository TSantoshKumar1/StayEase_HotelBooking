package com.airbnb.service;

import com.airbnb.dto.PropertyDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import jakarta.transaction.Transactional;

public interface PropertyService {

    @Transactional
    public void addProperty(PropertyDto propertyDto);

    @Transactional
    public void deletePropertyById(Long propertyId);

    @Transactional
    public void deletePropertyBylocationOrCountry(String locationName);

    @Transactional
    public Property updateProperty(Long propertyId , PropertyDto propertyDto);

    @Transactional
    public Property updateProperty(String propertyName, PropertyDto propertyDto);
}
