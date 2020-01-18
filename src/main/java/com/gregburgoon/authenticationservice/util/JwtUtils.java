package com.gregburgoon.authenticationservice.util;

import com.gregburgoon.authenticationservice.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.NoRepositoryBean;

import javax.annotation.ManagedBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public UserDTO parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            UserDTO u = new UserDTO();
            u.setEmail(body.getSubject());
            u.setUserId(Long.parseLong((String) body.get("userId")));
            //TODO: fix the [] in the parsing here
            String[] roles = ((String)body.get("roles")).split(",");
            List<String> rolesList = new ArrayList<String>(Arrays.asList(roles));
            u.setRoles(rolesList);
            return u;
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(UserDTO u) {
        Claims claims = Jwts.claims().setSubject(u.getEmail());
        claims.put("userId", u.getUserId() + "");
        claims.put("roles", u.getRoles().toString());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
