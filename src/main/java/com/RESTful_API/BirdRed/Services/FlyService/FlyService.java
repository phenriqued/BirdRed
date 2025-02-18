package com.RESTful_API.BirdRed.Services.FlyService;


import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.RequestFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.ResponseGetFlyDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Services.FlyService.FlyValidator.FlyValidation;
import com.RESTful_API.BirdRed.Services.UserService.UserValidator.UserValidator;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlyService {

    @Autowired
    private FlyRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private FlyValidation flyValidation;

    @Transactional
    public RequestFlyDTO createFly(RequestFlyDTO requestDTO, JwtAuthenticationToken token) {
        var user = userValidator.findUserActive(token.getName());
        repository.save(new Fly(new CreateFlyDTO(requestDTO.content(), user, TypeFly.ORIGINAL)));
        return requestDTO;
    }

    public ResponseGetFlyDTO getFlysByUser(String identify, Pageable pageable) {
        if(identify.contains("@")){
            throw new ValidationException("it is necessary to use the user's nickname");
        }

        var user = userValidator.findUserActive(identify);;

        List<FlyDTO> flys = flyValidation.findAllFlysByAuthor(user, pageable);

        return new ResponseGetFlyDTO(user.getNickname(), flys);
    }
    public FlyDTO getFlybyUser(String id) {
        var fly = flyValidation.findById(id);

        if(!fly.getAuthor().getIsActive()){
            throw new ValidationException("Author is disable");
        }
        return new FlyDTO(fly);
    }

    public FlyDTO updateUserFly(JwtAuthenticationToken token, RequestFlyDTO dto, String id) {
        Fly userFly = flyValidation.findById(id);
        var user = userValidator.findUserActive(token.getName());

        flyValidation.validateUserFlyOwnership(user, userFly, "Unable to update Fly: User is not the author");
        flyValidation.validateFlyTime(userFly);

        userFly.setContent(dto.content());
        userFly.setUpdatedAt(LocalDateTime.now());
        repository.save(userFly);
        return new FlyDTO(userFly);
    }

    public void deleteFly(String id, JwtAuthenticationToken token) {
        Fly userFly = flyValidation.findById(id);
        var user = userValidator.findUserActive(token.getName());

        flyValidation.validateUserFlyOwnership(user, userFly, "Unable to DELETE Fly: User is not the author");

        repository.deleteById(id);
    }



}
