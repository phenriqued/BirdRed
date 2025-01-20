package com.RESTful_API.BirdRed.Infra.DataLoader;

import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void run(String... args) throws Exception {
        saveRole();
    }

    private void saveRole(){
        mongoTemplate.save(new Role(1L, "admin"), "Roles");
        mongoTemplate.save(new Role(2L, "customer"), "Roles");
    }
}
