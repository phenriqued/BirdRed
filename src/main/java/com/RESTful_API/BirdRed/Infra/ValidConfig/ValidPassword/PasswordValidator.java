package com.RESTful_API.BirdRed.Infra.ValidConfig.ValidPassword;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[*!#$&-_])(?=\\S+$).{6,20}$";


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.isBlank()){
            return false;
        }
        return value.matches(PASSWORD_PATTERN);
    }
}
