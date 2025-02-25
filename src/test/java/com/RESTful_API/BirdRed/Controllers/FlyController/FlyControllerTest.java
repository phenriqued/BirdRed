package com.RESTful_API.BirdRed.Controllers.FlyController;

import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.DTOs.Fly.RequestFlyDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Com essa anotação Permite usar @BeforeAll e @AfterAll sem precisar de métodos estáticos
class FlyControllerTest {

    @Autowired
    private FlyRepository flyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JacksonTester<RequestFlyDTO> requestFlyJsonTester;

    @BeforeAll
    void beforeAll(){
        userRepository.save(new User("user","user@BirdRed.com", "teste123", new Role("CUSTOMER")));
    }
    @AfterAll
    void afterSetup(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Must return status \"200 Ok\" and return the Fly post")
    void createFly() throws Exception{
        mockMvc.perform(post("/Fly")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestFlyJsonTester
                                .write(new RequestFlyDTO("Hello World")).getJson())
                        .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Hello World"));
    }

    @Test
    @DisplayName("Must return status \"200 Ok\" and return the REFly post")
    void createReFly() throws Exception{
        User otherUser = userRepository.save(new User("teste","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));

        Fly originalFly = flyRepository.save(new Fly(new CreateFlyDTO("Hello World", otherUser, TypeFly.ORIGINAL)));

        mockMvc.perform(post("/Fly/"+originalFly.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestFlyJsonTester
                                .write(new RequestFlyDTO("Hello World Refly")).getJson())
                        .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello World Refly"))
                .andExpect(jsonPath("$.fly.id").value(originalFly.getId()));
    }

    @Test
    @DisplayName("Must return status \"200 Ok\" and return the Comment Fly post")
    void createCommentFly() throws Exception {
        User otherUser = userRepository.save(new User("teste","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));

        Fly originalFly = flyRepository.save(new Fly(new CreateFlyDTO("Hello World", otherUser, TypeFly.ORIGINAL)));

        mockMvc.perform(post("/Fly/"+originalFly.getId()+"/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestFlyJsonTester
                                .write(new RequestFlyDTO("Hello World Comment")).getJson())
                        .with(jwt().authorities(new SimpleGrantedAuthority("CUSTOMER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello World Comment"))
                .andExpect(jsonPath("$.fly.id").value(originalFly.getId()))
                .andExpect(jsonPath("$.type").value("COMMENT"));

    }
}