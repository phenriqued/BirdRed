package com.RESTful_API.BirdRed.Entities.RoleEntity;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum UserRoles {
    ADMIN(1L),
    CUSTOMER(2L);

    private final Long roleId;

    private static final Map<Long, UserRoles> ROLES_MAP = new HashMap<>();
    static{
        for(UserRoles role : UserRoles.values()){
            ROLES_MAP.put(role.getRoleId(), role);
        }
    }

    UserRoles(Long roleId) {
        this.roleId = roleId;
    }

    public static UserRoles valueOf(Long id){
        UserRoles role = ROLES_MAP.get(id);
        if(role == null){
            throw new IllegalArgumentException("Invalid role ID:" + id);
        }
        return role;
    }
}
