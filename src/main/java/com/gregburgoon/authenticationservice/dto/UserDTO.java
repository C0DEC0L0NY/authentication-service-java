package com.gregburgoon.authenticationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserDTO {
    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private List<String> roles;
}
