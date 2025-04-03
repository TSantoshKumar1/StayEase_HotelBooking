package com.airbnb.service;

import com.airbnb.entity.Image;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;

public interface ImageService {

    public Image addImage(String imageUrl , Property property , PropertyUser propertyUser);
}
