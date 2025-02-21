package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.CommentsFly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FlyDTO(
        String id,
        String content,
        TypeFly type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public FlyDTO(Fly entity){
        this(entity.getId(), entity.getContent(), entity.getType(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
    public FlyDTO(CommentsFly entity){
        this(entity.getId(), entity.getContent(), entity.getType(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

