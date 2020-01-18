package com.gregburgoon.authenticationservice;

import com.gregburgoon.authenticationservice.dto.UserDTO;
import com.gregburgoon.authenticationservice.entity.Role;
import com.gregburgoon.authenticationservice.exception.EmailExistsException;
import com.gregburgoon.authenticationservice.exception.InvalidCredentials;
import com.gregburgoon.authenticationservice.repository.AuthenticationRepository;
import com.gregburgoon.authenticationservice.dto.RegistrationDTO;
import com.gregburgoon.authenticationservice.entity.User;
import com.gregburgoon.authenticationservice.repository.RoleRepository;
import com.gregburgoon.authenticationservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public User registerNewUserAccount(RegistrationDTO registrationDTO) throws EmailExistsException {
        Optional<User> user = authenticationRepository.findUserByEmail(registrationDTO.getEmail());
        if (!user.isPresent()) {
            User newUser = new User();
            newUser.setFirstName(registrationDTO.getFirstName());
            newUser.setLastName(registrationDTO.getLastName());
            newUser.setPassword(passwordEncoder().encode(registrationDTO.getPassword()));
            newUser.setEmail(registrationDTO.getEmail());
            Optional<Role> role = roleRepository.findRoleByName("ROLE_USER");
            if (role.isPresent())
            {
                newUser.setRoles(Arrays.asList(role.get()));
            }
            return authenticationRepository.save(newUser);
        } else {
            throw new EmailExistsException();
        }
    }

    @Override
    public String getAuthToken(String email, String password) throws InvalidCredentials {
        Optional<User> optionalUser = authenticationRepository.login(email,passwordEncoder().encode(password));
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            UserDTO dto = new UserDTO();
            dto.setUserId(user.getId());
            dto.setEmail(user.getEmail());
            List<String> roles = new ArrayList<String>();
            roles.add("ROLE1");
            roles.add("ROLE2");
            dto.setRoles(roles);
            String token = jwtUtils.generateToken(dto);
            return token;
        } else {
            throw new InvalidCredentials();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
