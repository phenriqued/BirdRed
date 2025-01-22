package com.RESTful_API.BirdRed.Infra.DataLoader;

import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        saveRole();
        admin();
    }

    private void saveRole(){
        mongoTemplate.save(new Role(1L, "admin"), "Roles");
        mongoTemplate.save(new Role(2L, "customer"), "Roles");
    }

    private void admin(){
        var userAdmin = userRepository.findByNickname("admin");
        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin on");
                },
                () -> {
                    User admin = new User("admin", "admin@birdRed.com", "admin", UserRoles.ADMIN);
                    userRepository.save(admin);
                }
        );
    }

}
