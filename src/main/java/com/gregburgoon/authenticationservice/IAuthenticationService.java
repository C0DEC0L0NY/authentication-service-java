package com.gregburgoon.authenticationservice;

import com.gregburgoon.authenticationservice.dto.RegisteredDTO;
import com.gregburgoon.authenticationservice.dto.RegistrationDTO;
import com.gregburgoon.authenticationservice.entity.User;
import com.gregburgoon.authenticationservice.exception.EmailExistsException;
import com.gregburgoon.authenticationservice.exception.InvalidCredentials;
import org.springframework.stereotype.Service;


@Service
public interface IAuthenticationService {
    RegisteredDTO registerNewUserAccount(RegistrationDTO registrationDTO)
            throws EmailExistsException;

    String getAuthToken(String email, String password) throws InvalidCredentials;
}
