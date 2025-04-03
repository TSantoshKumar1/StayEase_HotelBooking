package com.airbnb.repository;

import com.airbnb.entity.PropertyPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyPackageRepository extends JpaRepository<PropertyPackage, Long> {

    public PropertyPackage findBypackageName(String packageName);
    public boolean existsBypackageName(String packageName);

}