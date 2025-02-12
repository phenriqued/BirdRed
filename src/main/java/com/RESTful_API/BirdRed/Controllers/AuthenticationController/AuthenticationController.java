package com.RESTful_API.BirdRed.Controllers.AuthenticationController;


import com.RESTful_API.BirdRed.DTOs.SignIn.SignInRequestDTO;
import com.RESTful_API.BirdRed.DTOs.SignIn.SignInResponseDTO;
import com.RESTful_API.BirdRed.DTOs.SignUp.SignUpRequestDTO;
import com.RESTful_API.BirdRed.DTOs.SignUp.SignUpResponseDTO;
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
    public ResponseEntity<SignInResponseDTO> login(@RequestBody @Valid SignInRequestDTO requestDTO){
        var token = signInService.login(requestDTO);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO requestDTO, UriComponentsBuilder uriBuilder){
        var basicUser = signUpService.createBasicUser(requestDTO);

        URI uri = uriBuilder.path("/{nickname}").buildAndExpand(basicUser.getNickname()).toUri();

        var token = signInService.login(new SignInRequestDTO(requestDTO.nickname(), requestDTO.password()));
        var response = new SignUpResponseDTO(basicUser, token.token());
        return ResponseEntity.created(uri).body(response);
    }


}
