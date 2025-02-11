package com.RESTful_API.BirdRed.Infra.Exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super("ERROR: \n["+message+"]");
    }
}
