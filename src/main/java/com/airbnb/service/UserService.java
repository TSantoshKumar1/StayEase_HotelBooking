package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;


public interface UserService {

    public PropertyUser addUser(PropertyUserDto propertyUserDto);

    public String verifyLogin(LoginDto loginDto);
}

