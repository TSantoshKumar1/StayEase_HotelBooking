package com.airbnb.service;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.PropertyUser;
import jakarta.transaction.Transactional;

import java.util.List;

public interface FavouriteService {

    public Favourite addFavourite(Favourite favourite , PropertyUser propertyUser);

    @Transactional
    public void deleteFavourite(Long propertyId , long propertyUser);

   public List<Favourite> fetchAllFavourite(PropertyUser propertyUser);
}
