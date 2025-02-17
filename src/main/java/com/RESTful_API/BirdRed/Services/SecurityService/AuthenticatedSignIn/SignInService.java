package com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedSignIn;


import com.RESTful_API.BirdRed.DTOs.SignIn.SignInRequestDTO;
import com.RESTful_API.BirdRed.DTOs.SignIn.SignInResponseDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.UserValidator;
import com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedService.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignInService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserValidator userValidator;

    @Transactional
    public SignInResponseDTO login(SignInRequestDTO userRequest){
        userValidator.activateAccount(userRequest.identify());
        var tokenAuthenticate = new UsernamePasswordAuthenticationToken(userRequest.identify(), userRequest.password());
        var authentication = authenticationManager.authenticate(tokenAuthenticate);
        return new SignInResponseDTO(authenticationService.authenticate(authentication));
    }

}
