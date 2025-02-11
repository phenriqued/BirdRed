package com.RESTful_API.BirdRed.Services.FlyService;


import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.RequestFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.ResponseGetFlyDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlyService {

    @Autowired
    private FlyRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RequestFlyDTO createFly(RequestFlyDTO requestDTO, JwtAuthenticationToken token) {

        var user = userRepository.findByNickname(token.getName())
                        .orElseThrow(() -> new ValidationException("User not exist!"));

        repository.save(new Fly(new CreateFlyDTO(requestDTO.content(), user, TypeFly.ORIGINAL)));

        return requestDTO;
    }

    public ResponseGetFlyDTO getFlysByUser(String identify, Pageable pageable) {
        if(identify.contains("@")){
            throw new ValidationException("it is necessary to use the user's nickname");
        }

        var user = userRepository.findByNickname(identify)
                .orElseThrow(() -> new ValidationException("User not exist!"));

        List<FlyDTO> flys = repository.findByAuthor(user, pageable).stream()
                .map(FlyDTO::new).toList();

        return new ResponseGetFlyDTO(user.getNickname(), flys);
    }
}
