package com.RESTful_API.BirdRed.Controllers.AdminController;

import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.DTOs.User.RequestDeleteUserByAdminDTO;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser(username = "admin01", roles = {"ADMIN"})
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JacksonTester<RequestDeleteUserByAdminDTO> requestDeleteJsonTest;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FlyRepository flyRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @AfterEach
    void afterSetup(){
        mongoTemplate.getDb().drop();
    }

    @Test
    @DisplayName("Must return status \"200 OK\" with list of basic users.")
    void listUsersScenarioOne() throws Exception {
        mockMvc.perform(get("/admin/list-user"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return status \"200 OK\" with basic users.")
    void findUser() throws Exception{
        User user = userRepository.save(new User("teste","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));

        mockMvc.perform(get("/admin/list-user/"+user.getNickname()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/admin/list-user/"+user.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return status \"200 OK\" and delete basic fly.")
    void deleteAnyFly() throws Exception{
        User user = userRepository.save(new User("teste","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));
        Fly fly = flyRepository.save(new Fly(new CreateFlyDTO("Hello World!", user, TypeFly.ORIGINAL)));

        mockMvc.perform(delete("/admin/Fly/"+fly.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return status \"200 OK\" and delete basic users.")
    void deleteAnyBasicUser() throws Exception{
        User user = userRepository.save(new User("teste","teste@BirdRed.com", "teste123", new Role("CUSTOMER")));

        mockMvc.perform(delete("/admin/"+user.getNickname())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDeleteJsonTest.write(
                                new RequestDeleteUserByAdminDTO(false)).getJson()))
                .andExpect(status().isOk());
    }

}