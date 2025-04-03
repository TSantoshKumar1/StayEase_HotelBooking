package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${Jwt.algorithm.key}")
    private String algorithmkey;

    @Value("${Jwt.issuer}")
    private String issuser;

    @Value("${Jwt.expiry.duration}")
    private  int expiryTime;

   private  Algorithm algorithm;

   private final static  String USER_NAME = "username";



    @PostConstruct
    public void PostConstruct(){

         algorithm = Algorithm.HMAC256(algorithmkey);

    }

public String generateToken(PropertyUser propertyUser){


   return JWT.create().withClaim(USER_NAME,propertyUser.getUserName())
            .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))
            .withIssuer(issuser)
            .sign(algorithm);

    }


    public String getUserName(String token){

        DecodedJWT decodeJwt = JWT.require(algorithm).withIssuer(issuser).build().verify(token);
      return   decodeJwt.getClaim(USER_NAME).asString();

    }


}
