package com.gregburgoon.authenticationservice;

import com.gregburgoon.authenticationservice.repository.AuthenticationRepository;
import com.gregburgoon.authenticationservice.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    @Mock
    AuthenticationRepository authenticationRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    AuthenticationService authenticationService;


    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterNewUserAccount() {

    }

    @Test
    void testGetAuthToken() {

    }
}