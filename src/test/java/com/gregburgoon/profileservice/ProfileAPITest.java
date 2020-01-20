package com.gregburgoon.profileservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gregburgoon.profileservice.dto.ProfileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
class ProfileAPITest {
    @Autowired
    private MockMvc mvc;

    private ProfileDTO.ProfileDTOBuilder profileDTOBuilder;
    private ObjectMapper mapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        profileDTOBuilder = ProfileDTO
                .builder()
                .email("test@test.com")
                .firstName("Greg")
                .lastName("Burgoon");
    }

}
