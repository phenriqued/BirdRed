package com.RESTful_API.BirdRed.Repositories.FlyRepository;


import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlyRepository extends MongoRepository<Fly, String> {

    List<Fly> findByAuthor(User author);

}
