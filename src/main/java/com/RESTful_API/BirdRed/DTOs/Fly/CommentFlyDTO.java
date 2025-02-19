package com.RESTful_API.BirdRed.DTOs.Fly;

import com.RESTful_API.BirdRed.Entities.FlyEntity.CommentsFly;

public record CommentFlyDTO(
        String nickname,
        String content

) {
    public CommentFlyDTO(CommentsFly commentsFly){
        this(commentsFly.getAuthor().getNickname(), commentsFly.getContent());
    }
}
