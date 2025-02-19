package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FlyCompleteDTO(
        String id,
        String content,
        TypeFly type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CommentFlyDTO> comments
) {
    public FlyCompleteDTO(Fly entity, List<CommentFlyDTO> comments){
        this(entity.getId(), entity.getContent(), entity.getType(), entity.getCreatedAt(), entity.getUpdatedAt(), comments);
    }
}
