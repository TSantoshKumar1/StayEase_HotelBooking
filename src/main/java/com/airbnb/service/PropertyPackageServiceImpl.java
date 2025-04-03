package com.airbnb.service;

import com.airbnb.dto.PropertyPackageDto;
import com.airbnb.entity.PropertyPackage;
import com.airbnb.repository.PropertyPackageRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PropertyPackageServiceImpl implements PropertyPackageService {

  private PropertyPackageRepository propertyPackageRepository;

    public PropertyPackageServiceImpl(PropertyPackageRepository propertyPackageRepository) {
        this.propertyPackageRepository = propertyPackageRepository;
    }



    @Override
    public void addPackage(PropertyPackageDto propertyPackageDto) {

        PropertyPackage propertyPackage = new PropertyPackage();
        propertyPackage.setPackageName(propertyPackageDto.getPackageName());
        propertyPackage.setServiceIncluded(propertyPackageDto.getServiceIncluded());
        propertyPackage.setOriginalPrice(propertyPackageDto.getOriginalPrice());
        propertyPackage.setDiscountPercentage(propertyPackageDto.getDiscountPercentage());
        propertyPackage.setFinalPrice(propertyPackageDto.getFinalPrice());
        propertyPackage.setDescription(propertyPackageDto.getDescription());
        propertyPackageRepository.save(propertyPackage);

    }

    @Override
    public PropertyPackage getPackage(Long packageId) {

        PropertyPackage propertyPackage = propertyPackageRepository.findById(packageId).get();

        return propertyPackage;

    }

    @Override
    public List<PropertyPackage> getALLpackage() {

        List<PropertyPackage> allPackage = propertyPackageRepository.findAll();
        return allPackage;
    }

    @Override
    public PropertyPackage updatePackage(Long packageId , PropertyPackageDto propertyPackageDto) {

        PropertyPackage propertyPackage = propertyPackageRepository.findById(packageId).get();

        if(propertyPackageDto.getPackageName()!=null){
           propertyPackage.setPackageName(propertyPackageDto.getPackageName());
       }
       if(propertyPackageDto.getServiceIncluded()!=null){
           propertyPackage.setServiceIncluded(propertyPackageDto.getServiceIncluded());
       }
       if(propertyPackageDto.getOriginalPrice()!=null){
           propertyPackage.setOriginalPrice(propertyPackageDto.getOriginalPrice());
       }
       if(propertyPackageDto.getDiscountPercentage()!=null){
           propertyPackage.setDiscountPercentage(propertyPackageDto.getDiscountPercentage());
       }
       if(propertyPackageDto.getFinalPrice()!=null){
           propertyPackage.setFinalPrice(propertyPackageDto.getFinalPrice());
       }
       if(propertyPackageDto.getDescription()!=null){
           propertyPackage.setDescription(propertyPackageDto.getDescription());
       }

        PropertyPackage savePackage = propertyPackageRepository.save(propertyPackage);

        return savePackage ;
    }

    @Override
    public void deletePackage(Long packageId) {

        propertyPackageRepository.deleteById(packageId);

        return;
    }

    @Override
    public PropertyPackage getPackageByName(String packageName) {

        PropertyPackage bypackageName = propertyPackageRepository.findBypackageName(packageName);
        return bypackageName;
    }
}
