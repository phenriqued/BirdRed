package com.RESTful_API.BirdRed.Controllers.AuthenticationController;


import com.RESTful_API.BirdRed.DTOs.SignInDTOs.SignInRequestDTO;
import com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedSignIn.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private SignInService signInService;


    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody SignInRequestDTO requestDTO){
        var token = signInService.login(requestDTO);
        return ResponseEntity.ok().body(token);
    }
}
