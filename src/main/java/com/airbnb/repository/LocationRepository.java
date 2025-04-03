package com.airbnb.repository;

import com.airbnb.entity.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Transactional
    public void deleteByLocationName(String locationName);

    public boolean existsByLocationName(String locationName);


}