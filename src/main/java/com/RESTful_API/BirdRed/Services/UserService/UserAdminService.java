package com.RESTful_API.BirdRed.Services.UserService;


import com.RESTful_API.BirdRed.DTOs.UserAdmin.ListUserDTO;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

}
