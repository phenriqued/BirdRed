package com.RESTful_API.BirdRed.Entities.UserEntity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "User")

@Getter
@NoArgsConstructor
public class User {

    @Id
    private String id;
    @Indexed(name = "nickname", unique = true, sparse = true)
    @Setter
    private String nickname;
    @Indexed(name = "email", unique = true, sparse = true)
    @Setter
    private String email;
    @Setter
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
