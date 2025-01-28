package com.RESTful_API.BirdRed.Infra.DataLoader;

import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Repositories.RolesRepository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RolesDataLoader implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public void run(String... args) throws Exception {
        saveRole();
    }
    private void saveRole(){
        rolesRepository.save(new Role(1L));
        rolesRepository.save(new Role(2L));
    }
}
