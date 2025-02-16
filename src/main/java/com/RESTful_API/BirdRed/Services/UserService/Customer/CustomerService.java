package com.RESTful_API.BirdRed.Services.UserService.Customer;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.DTOs.User.UserResponseDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlyRepository flyRepository;


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

    public void updateUser(String nickname, RequestUpdateUserDTO dto, JwtAuthenticationToken token) {
        User user = userRepository.findByNickname(token.getName())
                                                        .orElseThrow(() -> new BadCredentialsException("User not found!"));
        validateUserTokenOwnership(user, nickname);

        user.setUpdateUser(dto);

        userRepository.save(user);
    }

    private void validateUserTokenOwnership(User user, String nickname) {
        User userVerification = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ValidationException("User nickname not found!"));
        if(!user.getId().equals(userVerification.getId())){
            throw new ValidationException("You cannot change data that does not belong to you!!!");
        }
    }


}
