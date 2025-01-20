package com.RESTful_API.BirdRed.Repositories.RolesRepository;

import com.RESTful_API.BirdRed.Entities.RoleEntity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RolesRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
