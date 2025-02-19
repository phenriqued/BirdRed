package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.CommentsFly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseCommentFly(
        FlyDTO fly,
        String content,
        TypeFly type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public ResponseCommentFly(FlyDTO dto, CommentsFly entity){
        this(dto, entity.getContent(), entity.getType(), entity.getCreatedAt(), entity.getUpdatedAt());
    }


}
