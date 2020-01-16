package com.gregburgoon.authenticationservice;

    import com.gregburgoon.authenticationservice.dto.RegistrationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController("/auth")
public class AuthenticationController {
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody RegistrationDTO registrationDTO) {
        //Check no one else is registered
        //            if () {
        //
        //            }
                //Create a new user account
//            Credentials credentials = createUserAccount(accountDto, result);
        return ResponseEntity.ok("User is valid");
    }

    @RequestMapping(value = "/auth/getAuthToken", method = RequestMethod.POST)
    public String authenticate() {
        return "Auth Token Gradle!";
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
