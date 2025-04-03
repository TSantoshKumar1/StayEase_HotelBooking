package com.airbnb.service;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavouriteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteImpl implements  FavouriteService{

    private FavouriteRepository favouriteRepository;

    public FavouriteImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }


    @Override
    public Favourite addFavourite(Favourite favourite, PropertyUser propertyUser) {

        favourite.setPropertyUser(propertyUser);
        Favourite savedFavourite = favouriteRepository.save(favourite);


        return savedFavourite;
    }

    @Transactional
    @Override
    public void deleteFavourite(Long propertyId ,long propertyUserId) {

        favouriteRepository.deleteByPropertyIdAndPropertyUser(propertyId,propertyUserId);
    }




    @Override
    public List<Favourite> fetchAllFavourite(PropertyUser propertyUser) {

        List<Favourite> userFavourites = favouriteRepository.findByPropertyUser(propertyUser);

        return  userFavourites;
    }
}
