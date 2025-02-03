package com.RESTful_API.BirdRed.Controllers.HelloWorld;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/HelloBird")
public class HelloWorld {

    @GetMapping
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok().body("Hello \n \uD83D\uDC26");
    }
}
