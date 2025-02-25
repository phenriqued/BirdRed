package com.RESTful_API.BirdRed.Controllers.FeedController;


import com.RESTful_API.BirdRed.DTOs.Feed.FeedDTO;
import com.RESTful_API.BirdRed.Infra.SecurityConfig.SecurityConfiguration;
import com.RESTful_API.BirdRed.Services.FlyService.FlyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@Tag(name = "Feed Controller", description = "Responsible for listing Fly and creating the feed")
public class Feed {

    @Autowired
    private FlyService flyService;

    @GetMapping
    public ResponseEntity<List<FeedDTO>> getFeed(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok().body(flyService.getFeed(pageable)) ;
    }


}
