package com.gregburgoon.authenticationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
public class RegisteredDTO {
    @NotBlank(message = "Must have a user id")
    @Getter
    @JsonProperty("userId")
    private Long userId;
}
