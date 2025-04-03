package com.airbnb.config;

import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;



@Component
public class JWTRequestFilter extends OncePerRequestFilter {

  private JWTService jwtService;

  private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          String tokenHeader =   request.getHeader("Authorization");

          if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){

             String token = tokenHeader.substring(8,tokenHeader.length()-1);

              String userName = jwtService.getUserName(token);

              Optional<PropertyUser> byUserName = propertyUserRepository.findByUserName(userName);

              if(byUserName.isPresent()){

                  PropertyUser user = byUserName.get();


//         To keep the track of current user login.....
 //        and basically it is a class where we stored the user authentication details along with roles and credentials.
              UsernamePasswordAuthenticationToken authentication =
                      new UsernamePasswordAuthenticationToken(user,null,Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())));

              // here WebAuthentication means ip address of the client url which is send in authentication object
                  authentication.setDetails(new WebAuthenticationDetails(request));

              // here SecurityContextHolder is a place in spring security where securityContext is a object that stored in this
              //  SecurityContextHolder and we stored this authentication object into this securityContext object of SecurityContextHolder
              // class . and springSecurity fetch this stored authentication object from this contxt object.

               SecurityContextHolder.getContext().setAuthentication(authentication);

// but after process complete stored user details in this context object automatically removed .
              }

              System.out.println(token);
        }


        filterChain.doFilter(request,response);
    }
}
