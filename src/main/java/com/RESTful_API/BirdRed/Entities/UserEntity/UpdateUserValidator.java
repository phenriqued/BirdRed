package com.RESTful_API.BirdRed.Entities.UserEntity;

import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateUserValidator {

    @Autowired
    private UserRepository userRepository;


    public void validateUserTokenOwnership(User user, String nickname) {
        User userVerification = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ValidationException("User nickname not found!"));
        if(!user.getId().equals(userVerification.getId())){
            throw new ValidationException("You cannot change data that does not belong to you!!!");
        }
    }


    public void updateTime(User user, RequestUpdateUserDTO updateDTO) {
        if(updateDTO.nickname() != null && user.getUpdatedAt() != null){
            var timeToUpdate = user.getUpdatedAt().plusDays(30L);
            if(LocalDateTime.now().isBefore(timeToUpdate)){
                throw new ValidationException(
                        "Last update was on "+  user.getUpdatedAt().getMonth() +
                        " " + user.getUpdatedAt().getDayOfMonth() +
                        ", it can only be updated 30 days later");
            }
        }
    }


}
