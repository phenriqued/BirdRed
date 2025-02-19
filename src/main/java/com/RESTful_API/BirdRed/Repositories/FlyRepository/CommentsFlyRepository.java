package com.RESTful_API.BirdRed.Repositories.FlyRepository;


import com.RESTful_API.BirdRed.Entities.FlyEntity.CommentsFly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsFlyRepository extends MongoRepository<CommentsFly, String> {

    Page<CommentsFly> findByAuthor(User author, Pageable pageable);

    Page<CommentsFly> findByFly(Fly fly, Pageable pageable);

    void deleteAllByFly(Fly fly);

    void deleteAllByAuthor(User author);

}
