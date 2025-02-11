package com.RESTful_API.BirdRed.Entities.FlyEntity;


import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Document(collection = "Fly")

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Fly {

    @Id
    private String id;
    @Setter
    private String content;
    @DocumentReference
    private User author;
    private TypeFly type;


    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Fly(CreateFlyDTO dto) {
        this.content = dto.content();
        this.author = dto.author();
        this.type = dto.typeFly();
        this.createdAt = LocalDateTime.now();
    }

}
