package com.gregburgoon.profileservice.repository;

import com.gregburgoon.profileservice.entity.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findProfileByEmail(String email);
    Optional<Profile> findProfileByUserId(Long userId);

    @Query("SELECT p FROM profile p WHERE p.email = ?1 OR p.userId = ?2")
    Optional<Profile> checkForPreExistingUsers(String email, Long userId);
}
