package com.gregburgoon.authenticationservice.dto;

import com.gregburgoon.authenticationservice.constraint.ValidPassword;
import com.gregburgoon.authenticationservice.constraint.group.PrimaryValidations;
import com.gregburgoon.authenticationservice.constraint.group.SecondaryValidations;
import lombok.Builder;
import lombok.Getter;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@GroupSequence({PrimaryValidations.class, SecondaryValidations.class, CredentialsDTO.class})
@ValidPassword(groups = SecondaryValidations.class)
@Builder
public class CredentialsDTO {
    @NotBlank(message = "Must have a Password", groups = PrimaryValidations.class)
    @Getter
    private String password;

    @NotBlank(message = "Must have an Email", groups = PrimaryValidations.class)
    @Getter
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Invalid Email", groups = SecondaryValidations.class)
    private String email;
}
