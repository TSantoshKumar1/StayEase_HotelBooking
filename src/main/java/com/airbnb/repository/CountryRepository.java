package com.airbnb.repository;

import com.airbnb.entity.Country;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    @Transactional
    public void deleteByCountryName(String countryName);

    public Optional<Country>findByCountryName(String countryName);

}