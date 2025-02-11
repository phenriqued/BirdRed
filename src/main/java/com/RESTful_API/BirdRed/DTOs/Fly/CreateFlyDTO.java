package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;

public record CreateFlyDTO(
        String content,
        User author,
        TypeFly typeFly
    ) {

}
