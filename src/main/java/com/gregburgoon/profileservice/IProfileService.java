package com.gregburgoon.profileservice;

import com.gregburgoon.profileservice.dto.ProfileDTO;
import com.gregburgoon.profileservice.exception.ProfileCreationException;
import com.gregburgoon.profileservice.exception.ProfileNotFoundException;
import com.gregburgoon.profileservice.exception.ProfileUpdateException;
import org.springframework.stereotype.Service;

@Service
public interface IProfileService {
    ProfileDTO createNewProfile(ProfileDTO profileDTO) throws ProfileCreationException;
    ProfileDTO getProfileForUser(Long userId) throws ProfileNotFoundException;
    ProfileDTO updateProfileForUser(ProfileDTO profileDTO) throws ProfileUpdateException;
}
