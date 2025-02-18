package com.RESTful_API.BirdRed.DTOs.User;

import com.RESTful_API.BirdRed.Infra.ValidConfig.ValidPassword.PasswordValid;
import jakarta.validation.constraints.NotBlank;

public record RequestDeleteUserDTO(
        @NotBlank
        @PasswordValid
        String password,
        boolean deleteAccount
) {
}
