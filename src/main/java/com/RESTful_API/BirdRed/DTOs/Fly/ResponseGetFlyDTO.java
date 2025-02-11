package com.RESTful_API.BirdRed.DTOs.Fly;

import java.util.List;

public record ResponseGetFlyDTO (
        String nickname,
        List<FlyDTO> flys
){
}
