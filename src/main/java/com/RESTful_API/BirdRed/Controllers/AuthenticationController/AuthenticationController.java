package com.RESTful_API.BirdRed.Controllers.AuthenticationController;


import com.RESTful_API.BirdRed.DTOs.SignInDTOs.SignInRequestDTO;
import com.RESTful_API.BirdRed.DTOs.SignUpDTOs.SignUpRequestDTO;
import com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedSignIn.SignInService;
import com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedSignUp.SignUpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private SignUpService signUpService;


    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody @Valid SignInRequestDTO requestDTO){
        var token = signInService.login(requestDTO);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequestDTO requestDTO, UriComponentsBuilder uriBuilder){
        var basicUser = signUpService.createUser(requestDTO);
        URI uri = uriBuilder.path("/BirdRed/{nickname}").buildAndExpand(basicUser.getNickname()).toUri();
        return ResponseEntity.created(uri).body(basicUser);
    }


}
