package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private PropertyUserRepository propertyUserRepository;

    private JWTService jwtService;


    // here we add depency injection by using constructor......
    public UserServiceImpl(PropertyUserRepository propertyUserRepository, JWTService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }


    @Override
    public PropertyUser addUser(PropertyUserDto propertyUserDto) {

        PropertyUser propertyUser = new PropertyUser();

        propertyUser.setFirstName(propertyUserDto.getFirstName());
        propertyUser.setLastName(propertyUserDto.getLastName());
        propertyUser.setUserName(propertyUserDto.getUserName());
        propertyUser.setEmail(propertyUserDto.getEmail());

        // here we encrypt the password by using Bcrypt class of security....
        propertyUser.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));

        propertyUser.setUserRole(propertyUserDto.getUserRole());


        PropertyUser savedUser = propertyUserRepository.save(propertyUser);

        return savedUser;
    }




    @Override
    public String verifyLogin(LoginDto loginDto) {

        Optional<PropertyUser> byUserName = propertyUserRepository.findByUserName(loginDto.getUserName());
        if(byUserName.isPresent()){
            PropertyUser propertyUser = byUserName.get();
            if(BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())){

               return jwtService.generateToken(propertyUser);
            }
        }
        return null;
    }
}
