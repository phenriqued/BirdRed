package com.RESTful_API.BirdRed.Entities.UserEntity;


import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Document(collection = "User")

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class User {

    @Id
    private String id;
    @Indexed(name = "nickname", unique = true, sparse = true)
    private String nickname;
    @Indexed(name = "email", unique = true, sparse = true)
    @Setter
    private String email;
    private String password;

    private List<Fly> flys;

    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    private Role role;

    public User(String nickname, String email, String password, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.role = role;
    }

    public void setUpdateUser(RequestUpdateUserDTO updateUserDTO){
        if(updateUserDTO != null){
            setIfNotEmptyAndEqual(this.nickname, updateUserDTO.nickname(), this::setNickname);
            setIfNotEmptyAndEqual(this.email, updateUserDTO.email(), this::setEmail);
            setPassword(updateUserDTO.password());
            setUpdatedAt(LocalDateTime.now());
        }
    }

    private void setIfNotEmptyAndEqual(String value , String  newData, Consumer<String> setter){
        if(newData != null && !newData.isEmpty()){
            if(newData.equals(value)){
                throw new ValidationException("Unable to update: identical data");
            }
            setter.accept(newData);
        }
    }
    private void setPassword(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(password != null) {
            if (password.isEmpty() || passwordEncoder.matches(password, this.password) ) {
                throw new ValidationException("Cannot update password");
            }
            this.password = passwordEncoder.encode(password);
        }
    }
    private void setNickname(String nickname){
        if(nickname.contains("@")){
            throw new ValidationException("the nickname cannot have \"@\"");
        }
        this.nickname = nickname;
    }


}
