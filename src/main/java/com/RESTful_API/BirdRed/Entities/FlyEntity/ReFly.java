package com.RESTful_API.BirdRed.Entities.FlyEntity;


import com.RESTful_API.BirdRed.DTOs.Fly.CreateFlyDTO;
import com.RESTful_API.BirdRed.Entities.UserEntity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
public class ReFly extends Fly{

    @DocumentReference
    private Fly originalFly;

    public ReFly(User author, Fly originalFly, String message) {
        super(new CreateFlyDTO(message, author, TypeFly.REFLY));
        this.originalFly = originalFly;
    }

}
