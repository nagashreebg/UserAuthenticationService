package com.scaler.userauthenticationservice.controlleradvice;

import com.scaler.userauthenticationservice.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, ArrayIndexOutOfBoundsException.class,
            UserAlreadyExistsException.class, RuntimeException.class})
    public ResponseEntity<String> handleException(Exception ex){
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
