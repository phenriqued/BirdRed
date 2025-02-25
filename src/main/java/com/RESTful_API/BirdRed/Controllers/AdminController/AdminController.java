package com.RESTful_API.BirdRed.Controllers.AdminController;


import com.RESTful_API.BirdRed.DTOs.User.ListUserDTO;
import com.RESTful_API.BirdRed.DTOs.User.RequestDeleteUserByAdminDTO;
import com.RESTful_API.BirdRed.DTOs.User.UserResponseDTO;
import com.RESTful_API.BirdRed.Infra.SecurityConfig.SecurityConfiguration;
import com.RESTful_API.BirdRed.Services.UserService.Admin.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "Responsible for listing and deleting users and deleting FlyPosts")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class AdminController {

    @Autowired
    private UserAdminService adminService;


    @GetMapping("/list-user")
    @Operation(summary = "List all basic users")
    @ApiResponse(responseCode = "200", description = "List of successful users")
    @ApiResponse(responseCode = "403", description = "Do not have \"ADMIN\" authority to make the request")
    public ResponseEntity<List<ListUserDTO>> listUsers(@PageableDefault(size = 5, sort = "User.nickname") Pageable pageable){
        return ResponseEntity.ok().body(adminService.listUsers(pageable));
    }

    @GetMapping("/list-user/{identify}")
    public ResponseEntity<UserResponseDTO> findUser(@PathVariable("identify") String identify,
                                                        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable){
        return ResponseEntity.ok().body(adminService.findUser(identify, pageable));
    }

    @DeleteMapping("/Fly/{id}")
    public ResponseEntity<HttpStatus> deleteAnyFly(@PathVariable("id") String id){
        adminService.deleteAnyFly(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<HttpStatus> deleteAnyBasicUser(@PathVariable("nickname") String nickname, @RequestBody RequestDeleteUserByAdminDTO deleteAccount){
        adminService.deleteUser(nickname, deleteAccount);
        return ResponseEntity.ok().build();
    }



}
