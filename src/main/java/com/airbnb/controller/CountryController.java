package com.airbnb.controller;

import com.airbnb.dto.CountryDto;
import com.airbnb.entity.Country;
import com.airbnb.repository.CountryRepository;
import com.airbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private CountryService countryService;
   private  CountryRepository countryRepository;

    public CountryController(CountryService countryService, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }


//    add country....
    @PostMapping("/addCountry")
    public ResponseEntity<String> addCountry(@RequestBody CountryDto countryDto){

        countryService.addCountry(countryDto);

        return new ResponseEntity<>("country added successful" , HttpStatus.CREATED);
    }




//    Delete country
    @DeleteMapping("/deleteCountry/{countryName}")
    public ResponseEntity<String> deleteCountry( @PathVariable String countryName){

        Optional<Country> byCountryName = countryRepository.findByCountryName(countryName);
       if(byCountryName.isPresent())
       {
           countryService.deleteCountry(countryName);
           return new ResponseEntity<>("country deleted successfully",HttpStatus.OK);
       }


       return new ResponseEntity<>("countryName does not exist",HttpStatus.CONFLICT);


    }


    @GetMapping("/fetchAllCountry")
    public ResponseEntity<List<Country>>fetchAllCountry(){

        List<Country> countries = countryService.getAllCountry();

        return new ResponseEntity<>(countries,HttpStatus.OK);
    }
}
