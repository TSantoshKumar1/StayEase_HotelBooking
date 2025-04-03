package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import com.airbnb.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private PropertyRepository propertyRepository;

    private ReviewRepository reviewRepository;

    private ReviewService reviewService;

    public ReviewController(PropertyRepository propertyRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }





    @PostMapping("/addReview/{property_id}")
    public ResponseEntity<String> addReview(@PathVariable long property_id,
                                            @RequestBody ReviewDto reviewDto ,
                                            @AuthenticationPrincipal PropertyUser propertyUser){



        Optional<Property> byId = propertyRepository.findById(property_id);
        Property property = byId.get();

        Review reviewByUser = reviewRepository.findReviewByUser(property, propertyUser);

        if(reviewByUser!=null) {

            return new ResponseEntity<>("your are already review this property", HttpStatus.OK);


        }

             reviewService.addReview(property ,reviewDto , propertyUser  );

            return new ResponseEntity<>("review added successful", HttpStatus.OK);
        }






 @GetMapping("/UserReview")
public ResponseEntity<List<Review>>fetchUserReviews(@AuthenticationPrincipal PropertyUser propertyUser){

     List<Review> userReview = reviewRepository.findByPropertyUser(propertyUser);
     return new ResponseEntity<>(userReview,HttpStatus.OK);


 }


    @GetMapping("/UserReviewByPropertyName/{propertyName}")
    public ResponseEntity<?>fetchReviewsByPropertyName(@PathVariable String propertyName,@AuthenticationPrincipal PropertyUser propertyUser){

        boolean propertyExists = propertyRepository.existsByPropertyName(propertyName);

        if(propertyExists){

            List<Review> reviews = reviewService.fetchReviewsByPropertyName(propertyName);
            if(reviews.isEmpty()){

                return new ResponseEntity<>("Review does not exist by this property",HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(reviews,HttpStatus.OK);
        }

        return new ResponseEntity<>("propertName does not exist",HttpStatus.CONFLICT);
    }





@DeleteMapping("/deleteReviewByPropertyId/{propertyId}")
public ResponseEntity<?>deleteReview(@PathVariable Long propertyId){

    boolean existsProperty = propertyRepository.existsById(propertyId);
    if(existsProperty){

        Integer deletedRows = reviewService.deleteReviewByid(propertyId);
        if(deletedRows>0){


            return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("Review is not deleted",HttpStatus.CONFLICT);
    }


        return new ResponseEntity<>("Property not found",HttpStatus.CONFLICT);
}




@PutMapping("/updateReview/{propertyId}")
public ResponseEntity<?>updateReview(@RequestBody ReviewDto reviewDto , @PathVariable long propertyId , @AuthenticationPrincipal PropertyUser propertyUser){


        Review userReview = reviewRepository.findReviewByPropertyIdAndUser(propertyId, propertyUser);
        if(userReview!=null){


        Review review = reviewService.updtaeReview(userReview, reviewDto);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    return new ResponseEntity<>("ths property Review does not exist",HttpStatus.CONFLICT);

}

}
