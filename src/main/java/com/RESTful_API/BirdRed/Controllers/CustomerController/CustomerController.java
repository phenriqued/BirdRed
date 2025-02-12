package com.RESTful_API.BirdRed.Controllers.CustomerController;


import com.RESTful_API.BirdRed.DTOs.UserAdmin.UserResponseDTO;
import com.RESTful_API.BirdRed.Services.UserService.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
