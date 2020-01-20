package com.gregburgoon.profileservice;

import com.gregburgoon.profileservice.dto.ProfileDTO;
import com.gregburgoon.profileservice.entity.Profile;
import com.gregburgoon.profileservice.exception.ProfileCreationException;
import com.gregburgoon.profileservice.exception.ProfileNotFoundException;
import com.gregburgoon.profileservice.exception.ProfileUpdateException;
import com.gregburgoon.profileservice.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public ProfileDTO createNewProfile(ProfileDTO profileDTO) throws ProfileCreationException {
        Profile profile = profileDTO.createProfileFromDTO();
        Optional<Profile> savedProfile = saveProfile(profile);
        if (savedProfile.isPresent()) {
            return ProfileDTO.fromProfile(savedProfile.get());
        }
        throw new ProfileCreationException();
    }

    @Override
    public ProfileDTO getProfileForUser(Long userId) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findProfileByUserId(userId);
        if (profile.isPresent()) {
            ProfileDTO profileDTO = ProfileDTO.fromProfile(profile.get());
            return profileDTO;
        }
        throw new ProfileNotFoundException();
    }

    @Override
    public ProfileDTO updateProfileForUser(ProfileDTO profileDTO) throws ProfileUpdateException {
        Optional<Profile> retrievedProfile = profileRepository.findProfileByUserId(profileDTO.getUserId());
        if (retrievedProfile.isPresent()) {
            Profile currentProfile = retrievedProfile.get();
            Profile profileUpdates = profileDTO.createProfileFromDTO();
            profileUpdates.setId(currentProfile.getId());
            Optional<Profile> savedProfile = saveProfile(profileUpdates);
            if (savedProfile.isPresent()) {
                return ProfileDTO.fromProfile(savedProfile.get());
            }
        }
        throw new ProfileUpdateException();
    }


    private Optional<Profile> saveProfile(Profile profile) {
        Optional<Profile> savedProfile = Optional.empty();
        try {
            savedProfile = Optional.of(profileRepository.save(profile));
        } catch (IllegalArgumentException e) {
            //TODO: add Logging
//            Logger.getLogger()
        }
        return savedProfile;
    }
}
