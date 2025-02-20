package com.RESTful_API.BirdRed.Entities.UserEntity;


import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    private Boolean isActive;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    private Role role;

    @JsonIgnore
    @Transient
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User(String nickname, String email, String password, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.role = role;
        this.isActive = true;
    }

    private void setPassword(String password){
        if(password != null) {
            if (password.isEmpty() || passwordValidator(password)) {
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

    public void updateIsActive() {
        this.isActive = !this.isActive;
    }

    public Boolean passwordValidator(String password){
        return passwordEncoder.matches(password, this.password);
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
        if(newData != null && !newData.isEmpty() && !newData.equals(value)) {
            setter.accept(newData);
        } else if (newData != null && newData.equals(value)) {
            throw new ValidationException("Unable to update: identical data");
        }
    }

}
