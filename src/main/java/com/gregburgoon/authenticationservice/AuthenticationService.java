package com.gregburgoon.authenticationservice;

import com.gregburgoon.authenticationservice.dto.RegisteredDTO;
import com.gregburgoon.authenticationservice.dto.RegistrationDTO;
import com.gregburgoon.authenticationservice.entity.Role;
import com.gregburgoon.authenticationservice.entity.User;
import com.gregburgoon.authenticationservice.exception.EmailExistsException;
import com.gregburgoon.authenticationservice.exception.InvalidCredentials;
import com.gregburgoon.authenticationservice.repository.AuthenticationRepository;
import com.gregburgoon.authenticationservice.repository.RoleRepository;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Value("${salt}")
    private String salt;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RegisteredDTO registerNewUserAccount(RegistrationDTO registrationDTO) throws EmailExistsException {
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
            authenticationRepository.save(newUser);
            RegisteredDTO.RegisteredDTOBuilder builder = RegisteredDTO.builder();
            builder.userId(newUser.getId());
            return builder.build();
        } else {
            throw new EmailExistsException();
        }
    }

    @Override
    public String getAuthToken(String email, String password) throws InvalidCredentials {
        Optional<User> optionalUser = authenticationRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            if (passwordEncoder().matches(password, user.getPassword())) {
                CommonProfile profile = new CommonProfile();
                profile.setId(user.getId().toString());
                for (Role role : user.getRoles()) {
                    profile.addRole(role.getName());
                }
                JwtGenerator<CommonProfile> jwtGenerator = new JwtGenerator();

                SecretSignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
                SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
                jwtGenerator.setSignatureConfiguration(secretSignatureConfiguration);
                jwtGenerator.setEncryptionConfiguration(secretEncryptionConfiguration);
                return jwtGenerator.generate(profile);
            }
        }
        throw new InvalidCredentials();
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
