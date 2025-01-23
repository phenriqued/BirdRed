package com.RESTful_API.BirdRed.DTOs.SignUpDTOs;


import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Infra.ValidConfig.ValidPassword.PasswordValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequestDTO(
        @NotBlank
        @Size(min = 6, max = 15, message = "The length must be between 6 and 15 characters")
        String nickname,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @PasswordValid
        String password
    ) {
    public SignUpRequestDTO{
        if(nickname.contains("@"))
            throw new ValidationException("the nickname cannot contains \"@\"");

    }


}
