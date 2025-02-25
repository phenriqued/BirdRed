package com.RESTful_API.BirdRed.Controllers.CustomerController;

import com.RESTful_API.BirdRed.DTOs.User.RequestUpdateUserDTO;
import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JacksonTester<RequestUpdateUserDTO> requestUpdateUserJsonTester;
    @Autowired
    MongoTemplate mongoTemplate;
    @BeforeEach
    void beforeSetup(){
        mongoTemplate.getDb().drop();
    }

    @Test
    @DisplayName("Must return status \"201 No Content\" and just update user nickname.")
    void updateBasicUserScenarioOne() throws Exception{
        User user = userRepository.save(new User("user","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));

        mockMvc.perform(put("/"+user.getNickname())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUpdateUserJsonTester
                        .write(new RequestUpdateUserDTO("testeUpdate", null ,null))
                        .getJson())
                .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER")))
        ).andExpect(status().isNoContent());

        user = userRepository.findById(user.getId()).orElseThrow();
        Assertions.assertEquals(user.getNickname(), "testeUpdate");
    }
    @Test
    @DisplayName("Must return status \"201 No Content\" and just update user email.")
    void updateBasicUserScenarioTwo() throws Exception{
        User user = userRepository.save(new User("user","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));

        mockMvc.perform(put("/"+user.getNickname())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUpdateUserJsonTester
                        .write(new RequestUpdateUserDTO(null, "testeBirdRed@gmail.com" ,null))
                        .getJson())
                .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER")))
        ).andExpect(status().isNoContent());

        user = userRepository.findById(user.getId()).orElseThrow();
        Assertions.assertEquals(user.getEmail(), "testeBirdRed@gmail.com");
    }
    @Test
    @DisplayName("Must return status \"201 No Content\" and just update user password.")
    void updateBasicUserScenarioThree() throws Exception{
        User user = userRepository.save(new User("user","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        mockMvc.perform(put("/"+user.getNickname())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUpdateUserJsonTester
                        .write(new RequestUpdateUserDTO(null, null ,"updatePassword123"))
                        .getJson())
                .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER")))
        ).andExpect(status().isNoContent());

        user = userRepository.findById(user.getId()).orElseThrow();
        Assertions.assertTrue(passwordEncoder.matches("updatePassword123", user.getPassword()));
    }
    @Test
    @DisplayName("should return \"400 Bad Request\" if you try to update data without changing anything in the current data .")
    void updateBasicUserScenarioFour() throws Exception{
        User user = userRepository.save(new User("user","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));
        mockMvc.perform(put("/"+user.getNickname())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUpdateUserJsonTester
                        .write(new RequestUpdateUserDTO("user", null ,null))
                        .getJson())
                .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER")))
        ).andExpect(status().isBadRequest());
    }


}