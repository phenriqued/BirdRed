package com.RESTful_API.BirdRed.Entities.FlyEntity;


import com.RESTful_API.BirdRed.DTOs.Fly.CreateCommentFlyDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Document(collection = "CommentsFly")

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class CommentsFly {

    @Id
    private String id;
    @Setter
    private String content;
    @DocumentReference
    private User author;
    @DocumentReference
    private Fly fly;

    private TypeFly type;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public CommentsFly(CreateCommentFlyDTO dto) {
        this.content = dto.content();
        this.author = dto.author();
        this.fly = dto.fly();
        this.type = TypeFly.COMMENT;
        this.createdAt = LocalDateTime.now();
    }
}
