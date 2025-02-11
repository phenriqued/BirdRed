package com.RESTful_API.BirdRed.Controllers.FlyController;


import com.RESTful_API.BirdRed.DTOs.Fly.RequestFlyDTO;
import com.RESTful_API.BirdRed.Services.FlyService.FlyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Fly")
public class FlyController {


    @Autowired
    private FlyService service;

    @PostMapping
    public ResponseEntity<RequestFlyDTO> createFly(@RequestBody @Valid RequestFlyDTO requestDTO,
                                                   JwtAuthenticationToken token){
        return ResponseEntity.ok().body(service.createFly(requestDTO, token));
    }

}
