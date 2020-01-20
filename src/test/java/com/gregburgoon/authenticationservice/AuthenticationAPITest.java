package com.gregburgoon.authenticationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gregburgoon.authenticationservice.dto.RegistrationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
class AuthenticationAPITest {
    @Autowired
    private MockMvc mvc;

    private RegistrationDTO.RegistrationDTOBuilder registrationDTOBuilder;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        registrationDTOBuilder = RegistrationDTO
                .buildRegistrationDTO()
                .email("test@test.com")
                .firstName("Greg")
                .lastName("Burgoon")
                .password("TestTest")
                .matchingPassword("TestTest");
    }

    @Test
    void testRegistrationSuccessful() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationEmptyFirstName() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.firstName("").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationEmptyLastName() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.lastName("").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationEmptyPassword() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.password("").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationEmptyPasswordMatch() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.matchingPassword("").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationPasswordMismatch() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.matchingPassword("NonMatchingPassword").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationPasswordLength() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.matchingPassword("small").password("small").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationEmptyEmail() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.email("").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testRegistrationInvalidEmail() throws Exception {
        RegistrationDTO registrationDTO = registrationDTOBuilder.email("asdfasdfasdfa").build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/auth/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(registrationDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }
}