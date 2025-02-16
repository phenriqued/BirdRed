package com.RESTful_API.BirdRed.Controllers.CustomerController;


import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.DTOs.User.UserResponseDTO;
import com.RESTful_API.BirdRed.Services.UserService.Customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("{nickname}")
    public ResponseEntity<UserResponseDTO> getCustomerByNickname(@PathVariable("nickname")String nickname,
                                                                 @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok().body(customerService.getUserCustomer(nickname, pageable)) ;
    }

    @PutMapping("{nickname}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid RequestUpdateUserDTO dto, @PathVariable("nickname")String nickname,
                                                 JwtAuthenticationToken token){
        customerService.updateUser(nickname, dto, token);
        return ResponseEntity.noContent().build();
    }

}
