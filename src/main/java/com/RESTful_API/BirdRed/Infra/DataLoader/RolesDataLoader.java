package com.RESTful_API.BirdRed.Infra.DataLoader;

import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Repositories.RolesRepository.RolesRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class RolesDataLoader implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void run(String... args) throws Exception {
        saveRole();
    }

    private void saveRole(){
        mongoTemplate.save(new Role(1L, UserRoles.ADMIN), "Roles");
        mongoTemplate.save(new Role(2L, UserRoles.CUSTOMER), "Roles");
    }

}
