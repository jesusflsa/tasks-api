package com.jesusfs.tasks.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.Collections;

public class JwtServiceImpl implements JwtService {
    @Value("jwt.private.key")
    private String PRIVATE_KEY;

    @Autowired
    private UserRepository userRepository;

    private Instant generateExpirationDate() {
        return Instant.now().plusSeconds(3600);
    }

    @Override
    public String createToken(UserModel user) {
        Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
        return JWT.create()
                .withIssuer("tasks-api")
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
            JWT.require(algorithm)
                    .withIssuer("tasks-api")
                    .build()
                    .verify(token);

            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;

        return auth.substring(7);
    }

    @Override
    public UserDetails getUserDetailsFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("tasks-api")
                .build()
                .verify(token);

        String username = decodedJWT.getSubject();
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
