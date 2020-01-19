package com.gregburgoon.authenticationservice;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pac4jConfig {

    @Value("${salt}")
    private String salt;

    @Bean
    public Config config() {
        //TODO: Figure out how to do the encryption and signature here correctly
//        SecretSignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
//        SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
        JwtAuthenticator authenticator = new JwtAuthenticator();
//        authenticator.setSignatureConfiguration(secretSignatureConfiguration);
//        authenticator.setEncryptionConfiguration(secretEncryptionConfiguration);
        HeaderClient jwtClient = new HeaderClient("Authorization", "Bearer", authenticator);
        Clients clients = new Clients(jwtClient);
        Config config = new Config(clients);
        return config;
    }
}