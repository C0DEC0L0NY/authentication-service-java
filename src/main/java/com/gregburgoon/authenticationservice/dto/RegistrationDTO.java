package com.gregburgoon.authenticationservice.dto;

import com.gregburgoon.authenticationservice.constraint.ValidRegistration;
import com.gregburgoon.authenticationservice.constraint.group.PrimaryValidations;
import com.gregburgoon.authenticationservice.constraint.group.SecondaryValidations;
import lombok.Builder;
import lombok.Getter;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

@GroupSequence({PrimaryValidations.class, SecondaryValidations.class, RegistrationDTO.class})
@ValidRegistration(groups = SecondaryValidations.class)
public class RegistrationDTO extends CredentialsDTO {

    @Builder(builderMethodName = "buildRegistrationDTO")
    public RegistrationDTO(String password, String email, String firstName, String lastName, String matchingPassword) {
        super(password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.matchingPassword = matchingPassword;
    }

    @NotBlank(message = "Must have a First Name", groups = PrimaryValidations.class)
    @Getter
    private String firstName;

    @NotBlank(message = "Must have a Last Name", groups = PrimaryValidations.class)
    @Getter
    private String lastName;

    @NotBlank(message = "Must have a Matching Password", groups = PrimaryValidations.class)
    @Getter
    private String matchingPassword;
}
