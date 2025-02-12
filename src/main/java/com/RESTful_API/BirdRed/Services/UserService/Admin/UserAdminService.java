package com.RESTful_API.BirdRed.Services.UserService.Admin;


import com.RESTful_API.BirdRed.DTOs.Fly.FlyDTO;
import com.RESTful_API.BirdRed.DTOs.UserAdmin.ListUserDTO;
import com.RESTful_API.BirdRed.DTOs.UserAdmin.UserResponseDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlyRepository flyRepository;

    public List<ListUserDTO> listUsers(Pageable pageable){
        return
                userRepository.findByRole(UserRoles.CUSTOMER, pageable).stream()
                        .map(ListUserDTO::new).toList();
    }

    public UserResponseDTO findUser(String identify, Pageable pageable){
        var user = identify.contains("@")
                ? userRepository.findByEmail(identify).orElseThrow(() -> new UsernameNotFoundException("User not found!"))
                : userRepository.findByNickname(identify).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        List<FlyDTO> flys = flyRepository.findByAuthor(user, pageable).stream()
                .map(FlyDTO::new).toList();
        return new UserResponseDTO(user, flys);
    }

    public void deleteAnyFly(String id) {
        if(!flyRepository.existsById(id)){
            throw new ValidationException("Fly not found. Check post ID!");
        }
        flyRepository.deleteById(id);
    }
}
