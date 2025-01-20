package com.RESTful_API.BirdRed.Entities.RoleEntity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;


@Document(collection = "Roles")

@Data
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    @Setter
    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
