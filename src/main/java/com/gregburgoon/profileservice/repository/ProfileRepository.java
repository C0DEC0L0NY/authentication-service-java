package com.gregburgoon.profileservice.repository;

import com.gregburgoon.profileservice.entity.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findProfileByEmail(String email);
    Optional<Profile> findProfileByUserId(Long userId);
}
