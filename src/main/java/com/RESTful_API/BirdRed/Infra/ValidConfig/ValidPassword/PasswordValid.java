package com.RESTful_API.BirdRed.Infra.ValidConfig.ValidPassword;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {

    String message() default "Password must have at least one uppercase letter, one number, one special character, and be between 5 and 20 characters long.";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
