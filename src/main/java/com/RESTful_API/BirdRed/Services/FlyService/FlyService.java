package com.RESTful_API.BirdRed.Services.FlyService;


import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.RequestFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.ResponseGetFlyDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
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

    @Transactional
    public RequestFlyDTO createFly(RequestFlyDTO requestDTO, JwtAuthenticationToken token) {
        var user = findUser(token.getName());
        repository.save(new Fly(new CreateFlyDTO(requestDTO.content(), user, TypeFly.ORIGINAL)));
        return requestDTO;
    }

    public ResponseGetFlyDTO getFlysByUser(String identify, Pageable pageable) {
        if(identify.contains("@")){
            throw new ValidationException("it is necessary to use the user's nickname");
        }

        var user = findUser(identify);

        List<FlyDTO> flys = repository.findByAuthor(user, pageable).stream()
                .map(FlyDTO::new).toList();

        return new ResponseGetFlyDTO(user.getNickname(), flys);
    }
    public FlyDTO getFlybyUser(String id) {
        var fly = repository.findById(id).
                        orElseThrow(() -> new ValidationException("Fly not found!"));
        return new FlyDTO(fly);
    }

    public FlyDTO updateUserFly(JwtAuthenticationToken token, RequestFlyDTO dto, String id) {
        Fly userFly = repository.findById(id)
                                    .orElseThrow(() -> new ValidationException("FLY ID does not exist!"));
        var user = findUser(token.getName());

        validateUserFlyOwnership(user, userFly, "Unable to update Fly: User is not the author");
        validateFlyTime(userFly);

        userFly.setContent(dto.content());
        userFly.setUpdatedAt(LocalDateTime.now());
        repository.save(userFly);
        return new FlyDTO(userFly);
    }

    public void deleteFly(String id, JwtAuthenticationToken token) {
        Fly userFly = repository.findById(id)
                .orElseThrow(() -> new ValidationException("FLY ID does not exist!"));
        var user = findUser(token.getName());

        validateUserFlyOwnership(user, userFly, "Unable to DELETE Fly: User is not the author");

        repository.deleteById(id);
    }

    private User findUser(String name){
        return userRepository.findByNickname(name)
                .orElseThrow(() -> new ValidationException("User not exist!"));
    }

    private void validateUserFlyOwnership(User user, Fly userFly, String exceptionMessage){
        if(!userFly.getAuthor().getId().equals(user.getId()))
            throw new BadCredentialsException(exceptionMessage);
    }

    private void validateFlyTime(Fly fly){
        var timeValidation = fly.getCreatedAt().plusMinutes(30);
        var timeNow = LocalDateTime.now();
        if(timeNow.isAfter(timeValidation)){
            throw new ValidationException("Unable to update Fly: Time limit exceeded");
        }
    }



}
