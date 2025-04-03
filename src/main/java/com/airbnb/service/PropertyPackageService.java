package com.airbnb.service;

import com.airbnb.dto.PropertyPackageDto;
import com.airbnb.entity.PropertyPackage;

import java.util.List;

public interface PropertyPackageService {

    public void addPackage(PropertyPackageDto propertyPackageDto);

    public PropertyPackage getPackage(Long packageId);

    public List<PropertyPackage> getALLpackage();

    public PropertyPackage updatePackage(Long packageId ,PropertyPackageDto propertyPackageDto);

   public  void deletePackage(Long packageId);

   public  PropertyPackage getPackageByName(String packageName);
}
