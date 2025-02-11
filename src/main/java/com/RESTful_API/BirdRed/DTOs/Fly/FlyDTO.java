package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;

import java.time.LocalDateTime;

public record FlyDTO(
        String id,
        String content,
        TypeFly type,
        LocalDateTime createdAt

) {
    public FlyDTO(Fly entity){
        this(entity.getId(), entity.getContent(), entity.getType(), entity.getCreatedAt());
    }


}
