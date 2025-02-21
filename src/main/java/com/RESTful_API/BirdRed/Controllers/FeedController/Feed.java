package com.RESTful_API.BirdRed.Controllers.FeedController;


import com.RESTful_API.BirdRed.DTOs.Feed.FeedDTO;
import com.RESTful_API.BirdRed.Services.FlyService.FlyService;
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
public class Feed {

    @Autowired
    private FlyService flyService;

    @GetMapping
    public ResponseEntity<List<FeedDTO>> getFeed(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok().body(flyService.getFeed(pageable)) ;
    }


}
