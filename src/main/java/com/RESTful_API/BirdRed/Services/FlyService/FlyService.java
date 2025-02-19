package com.RESTful_API.BirdRed.Services.FlyService;


import com.RESTful_API.BirdRed.DTOs.Fly.*;
import com.RESTful_API.BirdRed.Entities.FlyEntity.CommentsFly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.Fly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.ReFly;
import com.RESTful_API.BirdRed.Entities.FlyEntity.TypeFly;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.CommentsFlyRepository;
import com.RESTful_API.BirdRed.Services.FlyService.FlyValidator.FlyValidation;
import com.RESTful_API.BirdRed.Services.UserService.UserValidator.UserValidator;
import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.RESTful_API.BirdRed.Repositories.FlyRepository.FlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlyService {

    @Autowired
    private FlyRepository repository;

    @Autowired
    private CommentsFlyRepository commentFlyRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private FlyValidation flyValidation;

    @Transactional
    public RequestFlyDTO createFly(RequestFlyDTO requestDTO, JwtAuthenticationToken token) {
        var user = userValidator.findUserActive(token.getName());
        repository.save(new Fly(new CreateFlyDTO(requestDTO.content(), user, TypeFly.ORIGINAL)));
        return requestDTO;
    }

    @Transactional
    public ReflyDTO createRefly(String postId, RequestFlyDTO requestFlyDTO, JwtAuthenticationToken token){
        var user = userValidator.findUserActive(token.getName());
        var fly = flyValidation.findById(postId);
        if(fly.getAuthor().getId().equals(user.getId())){
            throw new ValidationException("It is not possible to share the post itself");
        }
        repository.save(new ReFly(user, fly, requestFlyDTO.content()));
        return new ReflyDTO(requestFlyDTO.content(), new FlyDTO(fly));
    }
    @Transactional
    public ResponseCommentFly createCommentFly(String id, RequestFlyDTO requestDTO, JwtAuthenticationToken token) {
        var user = userValidator.findUserActive(token.getName());
        var fly = flyValidation.findById(id);
        var comment = commentFlyRepository.save(
                new CommentsFly(new CreateCommentFlyDTO(requestDTO.content(), user, fly)));
        return new ResponseCommentFly(new FlyDTO(fly),comment);
    }

    public ResponseGetFlyDTO getFlysByUser(String identify, Pageable pageable) {
        if(identify.contains("@")){
            throw new ValidationException("it is necessary to use the user's nickname");
        }
        var user = userValidator.findUserActive(identify);;
        List<FlyDTO> flys = flyValidation.findAllFlysByAuthor(user, pageable);
        return new ResponseGetFlyDTO(user.getNickname(), flys);
    }
    public FlyCompleteDTO getFlybyUser(String id) {
        var fly = flyValidation.findById(id);
        List<CommentFlyDTO> comments = commentFlyRepository.findByFly(fly, Pageable.unpaged()).stream()
                .map(CommentFlyDTO::new).toList();

        if(!fly.getAuthor().getIsActive()){
            throw new ValidationException("Author is disable");
        }
        return new FlyCompleteDTO(fly, comments);
    }

    @Transactional
    public FlyDTO updateUserFly(JwtAuthenticationToken token, RequestFlyDTO dto, String id) {
        Fly userFly = flyValidation.findById(id);
        var user = userValidator.findUserActive(token.getName());

        flyValidation.validateUserFlyOwnership(user, userFly, "Unable to update Fly: User is not the author");
        flyValidation.validateFlyTime(userFly);

        userFly.setContent(dto.content());
        userFly.setUpdatedAt(LocalDateTime.now());
        repository.save(userFly);
        return new FlyDTO(userFly);
    }

    @Transactional
    public void deleteFly(String id, JwtAuthenticationToken token) {
        Fly userFly = flyValidation.findById(id);
        var user = userValidator.findUserActive(token.getName());

        flyValidation.validateUserFlyOwnership(user, userFly, "Unable to DELETE Fly: User is not the author");
        commentFlyRepository.deleteAllByFly(userFly);
        repository.deleteById(id);
    }



}
