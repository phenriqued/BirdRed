package com.RESTful_API.BirdRed.Infra.ErrorsHandlers;


import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handler400error(MethodArgumentNotValidException exception){
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidationDTO::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handler400(ValidationException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handlerBadCredentials(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handlerAuthenticationException(AuthenticationException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity mongoException(MongoWriteException exception){
        return ResponseEntity.badRequest().body("nickname or email already exists");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        System.out.println("🚨 Exception caught: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal ERROR: " + e.getMessage());
    }




    private record DataErrorValidationDTO(String fliedError, String defaultMessage){
        public DataErrorValidationDTO(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
