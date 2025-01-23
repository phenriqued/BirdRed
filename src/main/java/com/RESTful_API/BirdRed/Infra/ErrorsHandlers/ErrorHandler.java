package com.RESTful_API.BirdRed.Infra.ErrorsHandlers;


import com.RESTful_API.BirdRed.Infra.Exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
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




    private record DataErrorValidationDTO(String fliedError, String defaultMessage){
        public DataErrorValidationDTO(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
