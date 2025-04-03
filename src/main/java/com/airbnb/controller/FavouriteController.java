package com.airbnb.controller;


import com.airbnb.entity.Favourite;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavouriteRepository;
import com.airbnb.service.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {


    private FavouriteService favouriteService;
    private FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteService favouriteService, FavouriteRepository favouriteRepository) {
        this.favouriteService = favouriteService;
        this.favouriteRepository = favouriteRepository;
    }


    @PostMapping("/addFavourite")
    public ResponseEntity<Favourite> addFavourite(@RequestBody Favourite favourite,
                                                  @AuthenticationPrincipal PropertyUser propertyUser) {

        Favourite userFavourite = favouriteService.addFavourite(favourite, propertyUser);

        return new ResponseEntity<>(userFavourite, HttpStatus.OK);

    }


    @DeleteMapping("deleteFavourite/{propertyId}")
    public ResponseEntity<String> deleteFavourite(@PathVariable long propertyId, @AuthenticationPrincipal PropertyUser propertyUser) {

        Long propertyUserId = propertyUser.getId();
        Favourite favourire = favouriteRepository.findFavouriteByPropertyIdAndPropertyUser(propertyId, propertyUserId);
        if (favourire != null) {

            favouriteService.deleteFavourite(propertyId, propertyUserId);
            return new ResponseEntity<>("favourite deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>(" Not deleted cause this property is not in your favourite list", HttpStatus.CONFLICT);

    }


    @GetMapping("/userFavourite")
    public ResponseEntity<?> fetchAllFvaourite(@AuthenticationPrincipal PropertyUser propertyUser) {


        List<Favourite> userFavourite = favouriteService.fetchAllFavourite(propertyUser);
        if (userFavourite.isEmpty()) {

            return new ResponseEntity<>("You don not have  favourite property ", HttpStatus.CONFLICT);

        }
        return new ResponseEntity<>(userFavourite, HttpStatus.OK);

    }


}