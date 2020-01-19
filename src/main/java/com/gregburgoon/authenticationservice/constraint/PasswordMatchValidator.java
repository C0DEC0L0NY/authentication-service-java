package com.gregburgoon.authenticationservice.constraint;

import com.gregburgoon.authenticationservice.dto.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<ValidRegistration, Object> {


    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationDTO registrationDTO = (RegistrationDTO) obj;
        String errorMessage = "";
        boolean isValid = true;
        if (!registrationDTO.getPassword().equals(registrationDTO.getMatchingPassword())) {
            isValid = false;
            errorMessage = "Passwords don't match";
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        }
        return isValid;
    }
}
