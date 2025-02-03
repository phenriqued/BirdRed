package com.RESTful_API.BirdRed.DTOs.SignIn;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDTO(
        @NotBlank
        String identify,
        @NotBlank
        String password) {
}
