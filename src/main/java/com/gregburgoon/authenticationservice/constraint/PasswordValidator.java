package com.gregburgoon.authenticationservice.constraint;

import com.gregburgoon.authenticationservice.dto.CredentialsDTO;
import com.gregburgoon.authenticationservice.dto.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CredentialsDTO registrationDTO = (CredentialsDTO) obj;
        String errorMessage = "";
        boolean isValid = true;
        if (!(registrationDTO.getPassword().length() >= 8)) {
            isValid = false;
            errorMessage = "Password isn't long enough, it must be 8 characters or longer";
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        }
        return isValid;
    }
}
