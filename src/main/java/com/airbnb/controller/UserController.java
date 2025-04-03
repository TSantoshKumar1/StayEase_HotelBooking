package com.airbnb.controller;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.dto.TokenResponse;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private final PropertyRepository propertyRepository;
    private PropertyUserRepository propertyUserRepository;


    // here we add dependency injection by using constructor........
    public UserController(UserService userService,
                          PropertyRepository propertyRepository, PropertyUserRepository propertyUserRepository) {
        this.userService = userService;
        this.propertyRepository = propertyRepository;
        this.propertyUserRepository = propertyUserRepository;
    }


    @PostMapping("/addUser")
    public ResponseEntity<String>addUser( @RequestBody  PropertyUserDto propertyUserDto){

        PropertyUser propertyUser = userService.addUser(propertyUserDto);

        if(propertyUser!=null){

            return  new ResponseEntity<>("Registration is Successful",HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Something went wrong ", HttpStatus.INTERNAL_SERVER_ERROR);
    }




    @PostMapping("/loginUser")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){

       String token = userService.verifyLogin(loginDto);

        if(token!=null){

          TokenResponse tokenResponse = new TokenResponse();
          tokenResponse.setToken(token);

            return new ResponseEntity<>(tokenResponse , HttpStatus.OK);
        }

        return new ResponseEntity<>("in valid credentials",HttpStatus.UNAUTHORIZED);
    }



// here @AuthenticationPrincipal take the  details of user object from the securityContecxt which stored in securitycontextHolder class
// which is a centeral place in spring security that holds the security and authentication details .

    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){

        return user;
    }


    @GetMapping("/AllUser")
    public ResponseEntity<List<PropertyUser>> getAllUser(){
        List<PropertyUser> allUsers = propertyUserRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }



}
