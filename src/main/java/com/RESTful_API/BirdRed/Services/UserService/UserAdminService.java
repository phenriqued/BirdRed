package com.RESTful_API.BirdRed.Services.UserService;


import com.RESTful_API.BirdRed.DTOs.UserAdmin.ListUserDTO;
import com.RESTful_API.BirdRed.DTOs.UserAdmin.UserResponseDTO;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminService {

    @Autowired
    private UserRepository userRepository;

    public List<ListUserDTO> listUsers(Pageable pageable){
        return
                userRepository.findByRole(UserRoles.CUSTOMER, pageable).stream()
                        .map(ListUserDTO::new).toList();
    }

    public UserResponseDTO findUser(String identify){
        return identify.contains("@")
                ? userRepository.findByEmail(identify)
                    .map(UserResponseDTO::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found!"))
                : userRepository.findByNickname(identify)
                    .map(UserResponseDTO::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }


}
