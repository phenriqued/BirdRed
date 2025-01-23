package com.RESTful_API.BirdRed.DTOs.SignUpDTOs;


public record SignUpRequestDTO(
        String nickname,
        String email,
        String password
    ) {


}
