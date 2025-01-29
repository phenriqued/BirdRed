package com.RESTful_API.BirdRed.Entities.UserEntity;


import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "User")


@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
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
    @Setter
    private LocalDateTime updatedAt;
    @DBRef
    private Role role;

    public User(String nickname, String email, String password, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.role = role;
    }

}
