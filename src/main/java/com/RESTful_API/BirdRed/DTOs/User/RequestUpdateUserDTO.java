package com.RESTful_API.BirdRed.DTOs.User;

import com.RESTful_API.BirdRed.Infra.ValidConfig.ValidPassword.PasswordValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RequestUpdateUserDTO(
        @Size(min = 4, max = 15, message = "The length must be between 6 and 15 characters")
        String nickname,
        @Email
        String email,
        @PasswordValid
        String password
)  {
}
