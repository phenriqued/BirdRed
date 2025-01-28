package com.RESTful_API.BirdRed.Entities.FlyEntity;


import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Fly")

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Fly {

    @Id
    private String id;
    private String content;
    private User author;



    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;





}
