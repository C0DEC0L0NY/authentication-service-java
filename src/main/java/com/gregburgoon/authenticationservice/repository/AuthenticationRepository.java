package com.gregburgoon.authenticationservice.repository;

import com.gregburgoon.authenticationservice.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthenticationRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT u FROM registered_user u where u.email = ?1 and u.password = ?2 ")
    Optional<User> login(String email, String password);

    Optional<User> findUserByEmail(String email);
}
