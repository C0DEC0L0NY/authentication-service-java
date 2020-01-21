package com.gregburgoon.profileservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gregburgoon.AuthenticationServiceApplication;
import com.gregburgoon.profileservice.dto.ProfileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AuthenticationServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
@ActiveProfiles("TestProfile")
class ProfileAPITest {
    @Autowired
    private MockMvc mvc;

    private ProfileDTO.ProfileDTOBuilder profileDTOBuilder;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        profileDTOBuilder = ProfileDTO
                .builder()
                .userId(1234L)
                .email("test@test.com")
                .firstName("Greg")
                .lastName("Burgoon");

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void testCreateProfileSuccess() throws Exception {
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());
    }

    @Test
    void testCreateProfileEmptyUserId() throws Exception{
        profileDTOBuilder.userId(null);
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testCreateProfileEmptyFirstName() throws Exception{
        profileDTOBuilder.firstName("");
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testCreateProfileEmptyLastName() throws Exception{
        profileDTOBuilder.lastName("");
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testCreateProfileEmptyEmail() throws Exception{
        profileDTOBuilder.email("");
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void testCreateProfileEmailExists() throws Exception{
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void testGetProfileSuccess() throws Exception {
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI.create("/user/1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetProfileBadUserId() throws Exception {
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI.create("/user/asdfasdf"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetProfileUserIdNotFound() throws Exception {
        ProfileDTO profileDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI.create("/user/4321"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void testUpdateProfileSuccess() throws Exception {
        ProfileDTO profileDTO = profileDTOBuilder.build();

        profileDTOBuilder.address("321 Fake St.");
        ProfileDTO updatedDTO = profileDTOBuilder.build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(profileDTO))).andReturn();
        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI.create("/user/1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(updatedDTO))).andReturn();

        assertEquals(HttpServletResponse.SC_OK, mvcResult.getResponse().getStatus());
    }
}

