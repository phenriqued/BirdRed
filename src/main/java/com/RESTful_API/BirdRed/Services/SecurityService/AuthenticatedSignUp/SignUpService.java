package com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedSignUp;


import com.RESTful_API.BirdRed.DTOs.SignUp.SignUpRequestDTO;
import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.RolesRepository.RolesRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public User createBasicUser(SignUpRequestDTO requestDTO){
        Role basicRole = roleRepository.findByName(UserRoles.CUSTOMER)
                .orElseThrow(() -> new ValidationException("Role not found!"));
        return
            userRepository.save(
                    new User(requestDTO.nickname(), requestDTO.email(), encoder.encode(requestDTO.password()), basicRole)
            );
    }


}
