package com.gregburgoon.authenticationservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthenticationController {
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public String register() {
        return "Register Gradle!";
    }

    @RequestMapping(value = "/auth/getAuthToken", method = RequestMethod.POST)
    public String authenticate() {
        return "Auth Token Gradle!";
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    public String logout() {
        return "Logout Gradle!";
    }

}
