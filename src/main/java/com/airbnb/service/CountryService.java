package com.airbnb.service;

import com.airbnb.dto.CountryDto;
import com.airbnb.entity.Country;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CountryService {

    public void addCountry(CountryDto countryDto);

    @Transactional
    public void deleteCountry(String countryName);

    public List<Country>getAllCountry();
}
