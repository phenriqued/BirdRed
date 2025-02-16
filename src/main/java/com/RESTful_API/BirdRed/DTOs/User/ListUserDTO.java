package com.RESTful_API.BirdRed.DTOs.User;

import com.RESTful_API.BirdRed.Entities.UserEntity.User;

import java.time.LocalDateTime;

public record ListUserDTO(
        String id,
        String nickname,
        String email,
        LocalDateTime createdAt,
        String role) {
    public ListUserDTO(User user){
        this(user.getId(), user.getNickname(), user.getEmail(), user.getCreatedAt(), user.getRole().getAuthority());
    }

}
