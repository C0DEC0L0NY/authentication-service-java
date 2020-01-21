package com.gregburgoon.profileservice;

import com.gregburgoon.profileservice.dto.ProfileDTO;
import com.gregburgoon.profileservice.entity.Profile;
import com.gregburgoon.profileservice.exception.ProfileCreationException;
import com.gregburgoon.profileservice.exception.ProfileNotFoundException;
import com.gregburgoon.profileservice.exception.ProfileUpdateException;
import com.gregburgoon.profileservice.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ProfileServiceTest {
    @Mock
    ProfileRepository profileRepository;

    @InjectMocks
    ProfileService service;

    ProfileDTO.ProfileDTOBuilder profileDTOBuilder;
    Profile profile;
    ArgumentCaptor<Profile> profileArgumentCaptor;

    @BeforeEach
    void setUp() {
        service = new ProfileService();
        MockitoAnnotations.initMocks(this);

        profileDTOBuilder = ProfileDTO
                .builder()
                .userId(1234L)
                .firstName("Greg")
                .lastName("Burgoon")
                .email("gre@greg.test")
                .photoUrl("http://fakephotourl.com")
                .address("123 Fake St.")
                .city("Toronto")
                .stateProvince("Ontario")
                .country("Canada");


        profile = new Profile();
        profile.setId(4321L);
        profile.setUserId(1234L);
        profile.setFirstName("Greg");
        profile.setLastName("Burgoon");
        profile.setEmail("gre@greg.test");
        profile.setPhotoUrl("http://fakephotourl.com");
        profile.setAddress("123 Fake St.");
        profile.setCity("Toronto");
        profile.setStateProvince("Ontario");
        profile.setCountry("Canada");

        profileArgumentCaptor = ArgumentCaptor.forClass(Profile.class);
        Mockito.when(profileRepository.findProfileByUserId(1234L)).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(any())).thenReturn(profile);
    }

    @Test
    void testProfileCreationSuccess() {
        ProfileDTO creationProfile = profileDTOBuilder.build();
        assertDoesNotThrow(() -> {
            ProfileDTO returnedProfile = service.createNewProfile(creationProfile);
            assertNotNull(returnedProfile);
            assertEquals(creationProfile, returnedProfile);
        });
    }

    @Test
    void testProfileCreationFailure() {
        ProfileDTO creationProfile = profileDTOBuilder.build();
        Mockito.when(profileRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(ProfileCreationException.class, () -> {
            service.createNewProfile(creationProfile);
        });
    }

    @Test
    void testProfileCreationFailureDueToExistingCredentials() {
        ProfileDTO creationProfile = profileDTOBuilder.build();
        Mockito.when(profileRepository.checkForPreExistingUsers(any(), any())).thenReturn(Optional.of(new Profile()));
        assertThrows(ProfileCreationException.class, () -> {
            service.createNewProfile(creationProfile);
        });
    }

    @Test
    void testGetProfileForUserSuccess() {
        assertDoesNotThrow(() -> {
            ProfileDTO profileDTO = service.getProfileForUser(1234L);
            ProfileDTO comparisonProfile = ProfileDTO.fromProfile(profile);
            assertNotNull(profileDTO);
            assertEquals(comparisonProfile, profileDTO);
        });
    }

    @Test
    void testGetProfileForUserFailure() {
        assertThrows(ProfileNotFoundException.class, () -> {
            service.getProfileForUser(8274L);
        });
    }

    @Test
    void testUpdateProfileForUserSuccess() {
        profileDTOBuilder.firstName("NewFirstName");
        profileDTOBuilder.lastName("NewLastName");
        ProfileDTO updatedProfile = profileDTOBuilder.build();
        assertDoesNotThrow(()-> {
            ProfileDTO profileDTO = service.updateProfileForUser(updatedProfile.getUserId(), updatedProfile);
            assertNotNull(profileDTO);
            Mockito.verify(profileRepository).save(profileArgumentCaptor.capture());
            assertEquals(profileArgumentCaptor.getValue().getFirstName(), "NewFirstName");
            assertEquals(profileArgumentCaptor.getValue().getLastName(), "NewLastName");
            assertEquals(profileArgumentCaptor.getValue().getId(), 4321L);
        });
    }

    @Test
    void testUpdateProfileForUserFailure() {
        profileDTOBuilder.userId(1387410279834719234L);
        profileDTOBuilder.lastName("NewLastName");
        ProfileDTO profileDTO = profileDTOBuilder.build();
        assertThrows(ProfileUpdateException.class, () -> {
            service.updateProfileForUser(profileDTO.getUserId(), profileDTO);
        });
    }
}