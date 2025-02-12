package com.RESTful_API.BirdRed.Services.UserService.Customer;

import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.UserAdmin.UserResponseDTO;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

}
