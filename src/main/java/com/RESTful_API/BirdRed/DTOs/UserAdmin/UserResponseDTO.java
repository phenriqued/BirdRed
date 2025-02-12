package com.RESTful_API.BirdRed.DTOs.UserAdmin;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDTO(
        String id,
        String nickname,
        String email,
        LocalDateTime createdAt,
        List<FlyDTO> flys
) {
    public UserResponseDTO(User user, List<FlyDTO> flys) {
        this(user.getId(), user.getNickname(), user.getEmail(), user.getCreatedAt(), flys);
    }
}
