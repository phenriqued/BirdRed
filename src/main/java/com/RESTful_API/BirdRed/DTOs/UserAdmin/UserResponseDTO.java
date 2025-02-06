package com.RESTful_API.BirdRed.DTOs.UserAdmin;

import com.RESTful_API.BirdRed.Entities.UserEntity.User;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String id,
        String nickname,
        String email,
        LocalDateTime createdAt
        //List<Fly> flys
) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getNickname(), user.getEmail(), user.getCreatedAt());
    }
}
