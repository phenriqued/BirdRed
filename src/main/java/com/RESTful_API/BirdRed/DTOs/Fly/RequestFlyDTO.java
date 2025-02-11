package com.RESTful_API.BirdRed.DTOs.Fly;

import jakarta.validation.constraints.Size;

public record RequestFlyDTO(
        @Size(min = 1, max = 280, message = "Content must be between 1 and 280 characters.")
        String content) {
}
