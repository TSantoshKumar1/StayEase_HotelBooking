package com.airbnb.controller;

import com.airbnb.entity.Image;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.ImageRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.BucketService;
import com.airbnb.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private BucketService bucketService;
    private ImageRepository imageRepository;
    private PropertyRepository propertyRepository;
    private ImageService imageService;


    public ImageController(BucketService bucketService, ImageRepository imageRepository, PropertyRepository propertyRepository, ImageService imageService) {
        this.bucketService = bucketService;
        this.imageRepository = imageRepository;
        this.propertyRepository = propertyRepository;
        this.imageService = imageService;
    }



    @PostMapping(value = "/upload/file/{bucketName}/property/{propertyId}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>addImage(@RequestParam MultipartFile file ,
                                     @PathVariable String bucketName ,
                                     @PathVariable Long propertyId,
                                     @AuthenticationPrincipal PropertyUser propertyUser) throws IOException {

        String imageUrl = bucketService.uploadFile(file, bucketName);

        Optional<Property> byId = propertyRepository.findById(propertyId);
        if(byId.isPresent()){

            Property property = byId.get();

            Image image = imageService.addImage(imageUrl, property, propertyUser);

            return new ResponseEntity<>(image , HttpStatus.OK);

        }
        return new ResponseEntity<>("unable to upload the image",HttpStatus.CONFLICT);


    }
}
