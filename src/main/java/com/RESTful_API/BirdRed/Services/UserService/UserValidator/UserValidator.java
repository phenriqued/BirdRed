package com.RESTful_API.BirdRed.Services.UserService.UserValidator;

import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserValidator {

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

    public void activateAccount(String identify){
        var userLogin = findUser(identify);
        if(!userLogin.getIsActive()){
            userLogin.updateIsActive();
            userRepository.save(userLogin);
        }
    }

    public void passwordValidation(User user, String password){
        if(!user.passwordValidator(password))
            throw new BadCredentialsException("Password is invalid");

    }

    public User findUser(String identify){
        return identify.contains("@")
                ? userRepository.findByEmail(identify).orElseThrow(() -> new UsernameNotFoundException("User is invalid!"))
                : userRepository.findByNickname(identify).orElseThrow(() -> new UsernameNotFoundException("User is invalid!"));
    }

    public User findUserActive(String identify){
        return identify.contains("@")
                ? userRepository.findByEmailAndIsActiveIsTrue(identify)
                                            .orElseThrow(() -> new ValidationException("User not Found!"))
                : userRepository.findByNicknameAndIsActiveIsTrue(identify)
                                            .orElseThrow(() -> new ValidationException("User not Found!"));
    }

}
