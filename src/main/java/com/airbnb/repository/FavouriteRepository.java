package com.airbnb.repository;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.PropertyUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {


    @Query("select f from Favourite f where f.property.id=:propertyId AND f.propertyUser.id=:propertyUserId")
    public Favourite findFavouriteByPropertyIdAndPropertyUser(@Param("propertyId") Long propertyId, @Param("propertyUserId") Long propertyUserId);


    @Transactional
    @Modifying
    @Query("Delete  FROM Favourite f where f.property.id=:propertyId AND f.propertyUser.id=:propertyUserId")
    public void deleteByPropertyIdAndPropertyUser(@Param("propertyId") Long propertyId, @Param("propertyUserId") Long propertyUserId);


    public List<Favourite> findByPropertyUser(PropertyUser propertyUser);

}