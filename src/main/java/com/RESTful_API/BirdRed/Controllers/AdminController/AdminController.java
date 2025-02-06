package com.RESTful_API.BirdRed.Controllers.AdminController;


import com.RESTful_API.BirdRed.DTOs.UserAdmin.ListUserDTO;
import com.RESTful_API.BirdRed.DTOs.UserAdmin.UserResponseDTO;
import com.RESTful_API.BirdRed.Services.UserService.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserAdminService adminService;


    @GetMapping("/list-user")
    public ResponseEntity<List<ListUserDTO>> listUsers(@PageableDefault(size = 5, sort = "User.nickname") Pageable pageable){
        return ResponseEntity.ok().body(adminService.listUsers(pageable));
    }

    @GetMapping("/list-user/{identify}")
    public ResponseEntity<UserResponseDTO> findUser(@PathVariable("identify") String identify){
        return ResponseEntity.ok().body(adminService.findUser(identify));
    }



}
