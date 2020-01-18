package com.gregburgoon.authenticationservice;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Config pac4jConfig;

    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/auth/**")
    );

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/user/**")
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final SecurityFilter filter = new SecurityFilter(pac4jConfig, "HeaderClient");


        http    .requestMatcher(PROTECTED_URLS)
                .addFilterBefore(filter, AnonymousAuthenticationFilter.class);

        http    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .exceptionHandling()
                    .and()
                .authorizeRequests()
                    .requestMatchers(PUBLIC_URLS).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();
    }
}
