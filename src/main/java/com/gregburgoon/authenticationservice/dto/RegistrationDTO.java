package com.gregburgoon.authenticationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gregburgoon.authenticationservice.constraint.group.PrimaryValidations;
import com.gregburgoon.authenticationservice.constraint.group.SecondaryValidations;
import com.gregburgoon.authenticationservice.constraint.ValidPassword;
import lombok.Builder;
import lombok.Getter;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@GroupSequence({PrimaryValidations.class, SecondaryValidations.class, RegistrationDTO.class})
@ValidPassword(groups = SecondaryValidations.class)
@Builder(toBuilder = true)
public class RegistrationDTO {
    @NotBlank(message = "Must have a First Name", groups = PrimaryValidations.class)
    @Getter
    @JsonProperty("firstname")
    private String firstName;

    @NotBlank(message = "Must have a Last Name", groups = PrimaryValidations.class)
    @Getter
    @JsonProperty("lastname")
    private String lastName;

    @NotBlank(message = "Must have a Password", groups = PrimaryValidations.class)
    @Getter
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Must have a Matching Password", groups = PrimaryValidations.class)
    @Getter
    @JsonProperty("matchingPassword")
    private String matchingPassword;

    @NotBlank(message = "Must have an Email", groups = PrimaryValidations.class)
    @Getter
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Invalid Email", groups = SecondaryValidations.class)
    @JsonProperty("email")
    private String email;
}
