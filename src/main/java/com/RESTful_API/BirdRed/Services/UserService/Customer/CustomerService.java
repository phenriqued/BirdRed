package com.RESTful_API.BirdRed.Services.UserService.Customer;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.User.RequestDeleteUserDTO;
import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.DTOs.User.UserResponseDTO;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.CommentsFlyRepository;
import com.RESTful_API.BirdRed.Services.FlyService.FlyValidator.FlyValidation;
import com.RESTful_API.BirdRed.Services.UserService.UserValidator.UserValidator;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
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
public class CustomerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FlyRepository flyRepository;
    @Autowired
    private CommentsFlyRepository commentsFlyRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private FlyValidation flyValidation;



    public UserResponseDTO getUserCustomer(String nickname, Pageable pageable) {
        if(nickname.contains("@")){
            throw new ValidationException("User cannot found: Use the user's nickname.");
        }
        var user = userValidator.findUserActive(nickname);
        List<FlyDTO> flys = flyValidation.findAllFlysByAuthor(user, pageable);

        return new UserResponseDTO(user, flys);
    }

    @Transactional
    public void updateUser(String nickname, RequestUpdateUserDTO dto, JwtAuthenticationToken token) {
        User user = userValidator.findUserActive(token.getName());

        userValidator.validateUserTokenOwnership(user, nickname);
        userValidator.updateTime(user, dto);
        user.setUpdateUser(dto);

        userRepository.save(user);
    }

    public void disableUser(String nickname, RequestDeleteUserDTO deleteUserDTO, JwtAuthenticationToken token) {
        User user = userValidator.findUserActive(token.getName());

        userValidator.validateUserTokenOwnership(user, nickname);
        userValidator.passwordValidation(user, deleteUserDTO.password());

        if(deleteUserDTO.deleteAccount()){
            commentsFlyRepository.deleteAllByAuthor(user);
            flyValidation.deleteAllFlyByAuthor(user);
            userRepository.deleteById(user.getId());
        }else{
            user.updateIsActive();
            userRepository.save(user);
        }
    }

}
