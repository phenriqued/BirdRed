package com.RESTful_API.BirdRed.Services.FlyService.FlyValidator;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FlyValidation {

    @Autowired
    private FlyRepository flyRepository;

    @Autowired
    private UserRepository userRepository;


    public void validateUserFlyOwnership(User user, Fly userFly, String exceptionMessage){
        if(!userFly.getAuthor().getId().equals(user.getId()))
            throw new BadCredentialsException(exceptionMessage);
    }

    public void validateFlyTime(Fly fly){
        var timeValidation = fly.getCreatedAt().plusMinutes(30);
        var timeNow = LocalDateTime.now();
        if(timeNow.isAfter(timeValidation)){
            throw new ValidationException("Unable to update Fly: Time limit exceeded");
        }
    }

    public List<FlyDTO> findAllFlysByAuthor(User user, Pageable pageable){
        return flyRepository.findByAuthor(user, pageable).stream()
                .map(FlyDTO::new).toList();
    }

    public Fly findById(String id){
        return flyRepository.findById(id).
                orElseThrow(() -> new ValidationException("Fly not found!"));
    }

    public void deleteAllFlyByAuthor(User user){
        if(!userRepository.existsById(user.getId())){
            throw new ValidationException("User not found!");
        }
        flyRepository.deleteAllByAuthor(user);
    }

}
