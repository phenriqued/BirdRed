package com.RESTful_API.BirdRed.Infra.DataLoader;

import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.RolesRepository.RolesRepository;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserAdminDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        admin();
    }

    private void admin(){
        var userAdmin = userRepository.findByNickname("admin");
        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin on");
                },
                () -> {
                    Role adminRole = rolesRepository.findByName(UserRoles.ADMIN)
                            .orElseThrow(() -> new ValidationException("Role not found!"));

                    User admin = new User("admin",
                            "admin@birdRed.com",
                            passwordEncoder.encode("admin@BirdRed"),
                            adminRole);

                    userRepository.save(admin);
                }
        );
    }
}
