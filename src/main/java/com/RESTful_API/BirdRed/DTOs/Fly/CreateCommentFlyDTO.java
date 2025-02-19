package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;

public record CreateCommentFlyDTO(
        String content,
        User author,
        Fly fly
    ) {

}
