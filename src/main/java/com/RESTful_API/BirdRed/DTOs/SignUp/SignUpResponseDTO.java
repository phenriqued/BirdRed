package com.RESTful_API.BirdRed.DTOs.SignUp;

import com.RESTful_API.BirdRed.Entities.UserEntity.User;

import java.time.LocalDateTime;

public record SignUpResponseDTO(
        String nickname,
        String email,
        LocalDateTime createdAt,
        String token
        ) {
    public SignUpResponseDTO(User user,String token){
        this(user.getNickname(), user.getEmail(), user.getCreatedAt(), token);
    }

}
