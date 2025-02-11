package com.RESTful_API.BirdRed.Entities.FlyEntity;

import lombok.Getter;

@Getter
public enum TypeFly {

    ORIGINAL("original"),
    REFLY("refly"),
    COMMENT("comment");

    private String type;

    TypeFly(String type) {
        this.type = type;
    }

}
