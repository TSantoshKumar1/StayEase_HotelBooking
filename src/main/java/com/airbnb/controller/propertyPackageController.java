package com.airbnb.controller;

import com.airbnb.dto.PropertyPackageDto;
import com.airbnb.entity.PropertyPackage;
import com.airbnb.repository.PropertyPackageRepository;
import com.airbnb.service.PropertyPackageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/propertyPackage")
public class propertyPackageController {

    private PropertyPackageRepository propertyPackageRepository;
    private PropertyPackageService propertyPackageService;

    public propertyPackageController(PropertyPackageRepository propertyPackageRepository, PropertyPackageService propertyPackageService) {
        this.propertyPackageRepository = propertyPackageRepository;
        this.propertyPackageService = propertyPackageService;
    }

    @PostMapping("/addPackage")
    public ResponseEntity<?>addPackage(@RequestBody PropertyPackageDto propertyPackageDto){


        propertyPackageService.addPackage(propertyPackageDto);
        return new ResponseEntity<>("package created successful", HttpStatus.CREATED);

    }



    @GetMapping("/fetchPackage/{packageId}")
    public ResponseEntity<?>getPackage(@PathVariable Long packageId){

        boolean exists = propertyPackageRepository.existsById(packageId);

        if(exists) {
            PropertyPackage SinglePackage = propertyPackageService.getPackage(packageId);

            return new ResponseEntity<>(SinglePackage, HttpStatus.OK);
        }
        return new ResponseEntity<>("packageId does not exist",HttpStatus.CONFLICT);
    }



    @GetMapping("fetchPackageByName/{packageName}")
   public ResponseEntity<?>getPackageByName(@PathVariable  String packageName) {

        boolean exist = propertyPackageRepository.existsBypackageName(packageName);
        if (exist) {

            PropertyPackage packageByName = propertyPackageService.getPackageByName(packageName);
            return new ResponseEntity<>(packageByName, HttpStatus.OK);
        }
        return new ResponseEntity<>("packageName does not exist",HttpStatus.CONFLICT);
    }



    @GetMapping("/fetchAllPackage")
    public ResponseEntity<List<PropertyPackage>>getAllPackage(){

        List<PropertyPackage> alLpackage = propertyPackageService.getALLpackage();
        return new ResponseEntity<>(alLpackage,HttpStatus.OK);
    }



    @PutMapping("/updatePackage/{packageId}")
    public ResponseEntity<?>updatePackage(@PathVariable Long packageId , @RequestBody PropertyPackageDto propertyPackageDto){

        boolean exists = propertyPackageRepository.existsById(packageId);
        if(exists){
            PropertyPackage propertyPackage = propertyPackageService.updatePackage(packageId,propertyPackageDto);
            return new ResponseEntity<>(propertyPackage , HttpStatus.OK);
        }
        return new ResponseEntity<>("packageId does not exist",HttpStatus.CONFLICT);

    }



    @DeleteMapping("/deletePackage/{packageId}")
    public ResponseEntity<?>deletePackage(@PathVariable Long packageId){

        boolean exists = propertyPackageRepository.existsById(packageId);
        if(exists){
            propertyPackageService.deletePackage(packageId);
            return new ResponseEntity<>("delete successfully" , HttpStatus.OK);
        }
        return new ResponseEntity<>("packageId does not exist" , HttpStatus.OK);
    }



}
