package com.airbnb.service;

import com.airbnb.entity.Image;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{

    private ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public Image addImage(String imageUrl, Property property, PropertyUser propertyUser) {

        Image image = new Image();
        image.setUrl(imageUrl);
        image.setProperty(property);
        image.setPropertyUser(propertyUser);

        Image savedImg = imageRepository.save(image);

        return savedImg;
    }
}
