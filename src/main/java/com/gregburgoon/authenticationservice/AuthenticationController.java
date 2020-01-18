package com.gregburgoon.authenticationservice;

import com.gregburgoon.authenticationservice.dto.RegistrationDTO;
import com.gregburgoon.authenticationservice.entity.User;
import com.gregburgoon.authenticationservice.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService service;

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody RegistrationDTO registrationDTO) {
        User registeredUser;
        try {
            registeredUser = service.registerNewUserAccount(registrationDTO);
            return ResponseEntity.ok("User email: "+ registeredUser.getEmail()+" is valid, and the user has been created");
        } catch (EmailExistsException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User Email Already Exists");
        }
    }

    @RequestMapping(value = "/auth/getAuthToken", method = RequestMethod.POST)
    public ResponseEntity authenticate() {
        return ResponseEntity.ok("Auth Token Gradle!");
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    public String logout() {
        return "Logout Gradle!";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ex.getBindingResult().getGlobalErrors().forEach((error) -> {
            String objectName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(objectName, errorMessage);
        });
        return errors;
    }
}
