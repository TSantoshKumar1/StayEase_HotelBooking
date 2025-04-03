package com.airbnb.service;

import com.airbnb.dto.CountryDto;
import com.airbnb.entity.Country;
import com.airbnb.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public void addCountry(CountryDto countryDto) {

        Country country = new Country();
        country.setCountryName(countryDto.getCountryName());

        countryRepository.save(country);

    }

    @Transactional
    @Override
    public void deleteCountry(String countryName) {

        countryRepository.deleteByCountryName(countryName);
    }



    @Override
    public List<Country> getAllCountry() {

        List<Country> countries = countryRepository.findAll();
        return countries;
    }


}
