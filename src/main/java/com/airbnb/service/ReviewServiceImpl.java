package com.airbnb.service;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements  ReviewService {




   private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }



    @Override
    public void addReview(Property property, ReviewDto reviewDto, PropertyUser propertyUser) {


        Review review = new Review();
        review.setContent(reviewDto.getContent());
        review.setProperty(property);
        review.setPropertyUser(propertyUser);

        reviewRepository.save(review);

    }

    @Override
    public List<Review>fetchReviewsByPropertyName(String propertyName) {
        List<Review> reviews = reviewRepository.findReviewsByPropertyName(propertyName);

        return  reviews;
    }

    @Transactional
    @Override
    public Integer deleteReviewByid(Long propertyId) {


        int deleteByPropertyId = reviewRepository.deleteByPropertyId(propertyId);

        return deleteByPropertyId;
    }


    @Transactional
    @Override
    public Review updtaeReview(Review userReview, ReviewDto reviewDto) {


        if(reviewDto.getContent()!=null){

            userReview.setContent(reviewDto.getContent());
        }

        if(reviewDto.getProperty()!=null){
            userReview.setProperty(reviewDto.getProperty());
        }

        if(reviewDto.getPropertyUser()!=null){
            userReview.setPropertyUser(reviewDto.getPropertyUser());
        }

        return userReview;
    }
}
