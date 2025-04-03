package com.airbnb.repository;

import com.airbnb.entity.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

  @Query("select p from Property p join Location l on p.location.id=l.id join Country c on p.country.id=c.id where l.locationName=:locationName or c.countryName=:locationName or p.propertyName=:locationName")
  public List<Property> findPropertyByLocation(@Param("locationName") String locationName);

  public Property findByPropertyName(String propertyName);

  @Query("SELECT p FROM Property p WHERE p.propertyName =:propertyName AND p.country.id = :countryId AND p.location.id =:locationId")
  public List<Property> findPropertyByPropertyNameAndLocatioinAndCountry(@Param("propertyName") String propertyName ,  @Param("countryId") Long countryId , @Param("locationId") Long locatioId );

  public boolean existsByPropertyName(String propertyName);

  @Transactional
  @Modifying
  @Query("delete from Property p where p.location.locationName=:locationName or p.country.countryName=:locationName or p.propertyName=:locationName")
  public void deleteAllPropertyBylocationOrCountry(@Param("locationName") String locationName);
}