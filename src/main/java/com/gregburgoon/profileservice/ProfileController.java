package com.gregburgoon.profileservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gregburgoon.profileservice.dto.ProfileDTO;
import com.gregburgoon.profileservice.exception.ProfileCreationException;
import com.gregburgoon.profileservice.exception.ProfileNotFoundException;
import com.gregburgoon.profileservice.exception.ProfileUpdateException;
import com.nimbusds.jose.util.JSONObjectUtils;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController("/user")
public class ProfileController {
    @Autowired
    ProfileService service;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity createProfile(@Valid @RequestBody ProfileDTO profileDTO) {
        try {
            ProfileDTO createdProfile = service.createNewProfile(profileDTO);
            return ResponseEntity.ok(jsonString(createdProfile));
        } catch (ProfileCreationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("The profile was not able to be created");
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity getProfile(@PathVariable(value="id") String id) {
        String errorMessage;
        try {
            Long userId = Long.valueOf(id);
            ProfileDTO profile = service.getProfileForUser(userId);
            return ResponseEntity.ok(jsonString(profile));
        } catch (ProfileNotFoundException e) {
            e.printStackTrace();
            errorMessage = "The profile for the user requested could not be found";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorMessage = "The userID is malformed";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "An unknown error occured";
        }
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public ResponseEntity updateProfile(@PathVariable(value="id") String id, @RequestBody ProfileDTO profileDTO) {
        String errorMessage;
        try {
            Long userId = Long.valueOf(id);
            ProfileDTO createdProfile = service.updateProfileForUser(userId, profileDTO);
            return ResponseEntity.ok(jsonString(createdProfile));
        } catch (ProfileUpdateException e) {
            e.printStackTrace();
            errorMessage = "The profile for this user couldn't be updated";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorMessage = "The userID is malformed";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "An unknown error occured";
        }
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private String jsonString(Object jsonObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(jsonObject);
    }

}
