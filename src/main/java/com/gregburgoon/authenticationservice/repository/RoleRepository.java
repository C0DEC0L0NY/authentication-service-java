package com.gregburgoon.authenticationservice.repository;

import com.gregburgoon.authenticationservice.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByName(@Param("name") String name);
}
