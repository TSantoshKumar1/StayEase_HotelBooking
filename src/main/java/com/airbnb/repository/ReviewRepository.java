package com.airbnb.repository;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    @Query("select r from Review r where r.property.id=:propertyId and r.propertyUser.id=:userId")
//    public Review findReviewByUserIdAndPropertyId( @Param("propertyId") Long propertyId,@Param("userId") Long userId );
//                                   or

    @Query("select r from Review r where r.property=:property and r.propertyUser=:propertyUser")
    public Review findReviewByUser(@Param("property") Property property , @Param("propertyUser") PropertyUser propertyUser);


    public List<Review>findByPropertyUser(PropertyUser propertyUser);

    @Query("select r from Review r where  r.property.propertyName=:propertyName")
    public List<Review> findReviewsByPropertyName(@Param("propertyName") String propertyName);


    @Transactional
    public Integer deleteByPropertyId(long propertyId);

    public boolean existsByPropertyId(long propertyId);

    @Query("select r from Review r where r.property.id=:propertyId And r.propertyUser=:propertyUser")
    public Review findReviewByPropertyIdAndUser(@Param("propertyId") long propertyId , @Param("propertyUser") PropertyUser propertyUser);

    public Review findByPropertyId(long propertyId);

}
