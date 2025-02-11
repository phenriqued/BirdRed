package com.RESTful_API.BirdRed.Repositories.FlyRepository;


import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlyRepository extends MongoRepository<Fly, String> {

    Page<Fly> findByAuthor(User author, Pageable pageable);

}
