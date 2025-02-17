package com.RESTful_API.BirdRed.Services.UserService.Customer;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.DTOs.User.UserResponseDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.UpdateUserValidator;
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
public class CustomerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FlyRepository flyRepository;
    @Autowired
    private UpdateUserValidator userValidator;


    public UserResponseDTO getUserCustomer(String nickname, Pageable pageable) {
        if(nickname.contains("@")){
            throw new ValidationException("User cannot found: Use the user's nickname.");
        }
        var user = userRepository.findByNickname(nickname)
                            .orElseThrow(() -> new ValidationException("User not found!"));
        List<FlyDTO> flys = flyRepository.findByAuthor(user, pageable).stream()
                                .map(FlyDTO::new).toList();

        return new UserResponseDTO(user, flys);
    }

    @Transactional
    public void updateUser(String nickname, RequestUpdateUserDTO dto, JwtAuthenticationToken token) {
        User user = userRepository.findByNickname(token.getName())
                                                        .orElseThrow(() -> new BadCredentialsException("User not found!"));

        userValidator.validateUserTokenOwnership(user, nickname);
        userValidator.updateTime(user, dto);
        user.setUpdateUser(dto);

        userRepository.save(user);
    }

}
