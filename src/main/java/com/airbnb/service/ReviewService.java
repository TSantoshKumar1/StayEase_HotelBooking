package com.airbnb.service;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ReviewService {

    public void addReview(Property property , ReviewDto reviewDto , PropertyUser propertyUser);

    public List<Review> fetchReviewsByPropertyName(String propertyName);

    @Transactional
   public Integer deleteReviewByid(Long propertyId);

    @Transactional
    public Review updtaeReview(Review userReview , ReviewDto reviewDto);


}
