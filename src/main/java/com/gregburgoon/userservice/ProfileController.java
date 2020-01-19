package com.gregburgoon.userservice;

import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.springframework.security.authentication.Pac4jAuthentication;
import org.pac4j.springframework.security.profile.SpringSecurityProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/user")
public class ProfileController {

    @RequestMapping(value = "/user/id", method = RequestMethod.GET)
    public ResponseEntity getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth instanceof Pac4jAuthentication) {
            Pac4jAuthentication token = (Pac4jAuthentication) auth;
            CommonProfile profile = token.getProfile();
            return ResponseEntity.ok("User Id is: "+profile.getId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/user/photo", method = RequestMethod.GET)
    public ResponseEntity getUserPhoto() {
        return ResponseEntity.ok("User Photo");
    }
}
