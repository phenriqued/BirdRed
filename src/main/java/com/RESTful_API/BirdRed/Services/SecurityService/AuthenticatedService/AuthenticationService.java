package com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private JwtService jwtService;
    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    public String authenticate(Authentication authentication){
        return jwtService.generateToken(authentication);
    }
}
