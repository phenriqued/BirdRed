package com.RESTful_API.BirdRed.Repositories.UserRepository;

import com.RESTful_API.BirdRed.Entities.RoleEntity.UserRoles;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    @Query("{ 'role.name' : ?0 }")
    Page<User> findByRole(UserRoles role, Pageable pageable);


}
