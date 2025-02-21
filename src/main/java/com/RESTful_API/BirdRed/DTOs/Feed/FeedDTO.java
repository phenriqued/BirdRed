package com.RESTful_API.BirdRed.DTOs.Feed;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;

public record FeedDTO(
        String nickname,
        FlyDTO flyDTO
) {
    public FeedDTO(Fly fly){
        this(fly.getAuthor().getNickname(), new FlyDTO(fly));
    }


}
