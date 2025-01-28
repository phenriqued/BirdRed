package com.RESTful_API.BirdRed.Entities.RoleEntity;


import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN(1L),
    CUSTOMER(2L);

    Long roleId;

    UserRoles(Long roleId) {
        this.roleId = roleId;
    }

    public static UserRoles valueOf(Long id){
        if(id == 1L) return ADMIN;
            else return CUSTOMER;
    }



}
