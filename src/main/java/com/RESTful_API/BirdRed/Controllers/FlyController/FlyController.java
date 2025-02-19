package com.RESTful_API.BirdRed.Controllers.FlyController;


import com.RESTful_API.BirdRed.DTOs.Fly.*;
import com.RESTful_API.BirdRed.Services.FlyService.FlyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/{id}")
    public ResponseEntity<ReflyDTO> createReFly(@PathVariable("id") String id, @RequestBody @Valid RequestFlyDTO requestDTO,
                                                JwtAuthenticationToken token){
        return ResponseEntity.ok().body(service.createRefly(id, requestDTO, token));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<ResponseCommentFly> createCommentFly(@PathVariable("id") String id, @RequestBody @Valid RequestFlyDTO requestDTO,
                                                               JwtAuthenticationToken token){
        return ResponseEntity.ok().body(service.createCommentFly(id, requestDTO, token));
    }

    @GetMapping("/listAll/{identify}")
    public ResponseEntity<ResponseGetFlyDTO> getFlysByUser(@PathVariable("identify") String identify,
                                                                                @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok().body(service.getFlysByUser(identify, pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<FlyCompleteDTO> getFly(@PathVariable("id") String id){
        return ResponseEntity.ok().body(service.getFlybyUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlyDTO> updateFly(@RequestBody RequestFlyDTO dto, @PathVariable("id") String id,
                                            JwtAuthenticationToken token){
        return ResponseEntity.ok().body(service.updateUserFly(token, dto, id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFly(@PathVariable() String id, JwtAuthenticationToken token){
        service.deleteFly(id, token);
        return ResponseEntity.noContent().build();
    }

}
