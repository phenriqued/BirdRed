package com.RESTful_API.BirdRed.Entities.RoleEntity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;


@Document(collection = "Roles")

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Role implements GrantedAuthority {

    @Id
    private String id;

    private UserRoles name;

    public Role(Long id) {
        this.name = UserRoles.valueOf(id);
    }

    public Role(String name) {
        this.name = UserRoles.valueOf(name);
    }

    @Override
    public String getAuthority() {
        return "ROLE_"+this.name;
    }
}
