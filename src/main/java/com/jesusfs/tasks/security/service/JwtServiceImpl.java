package com.jesusfs.tasks.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
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
        log.info("JwtServiceImpl::createToken execution started.");
        Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
        log.info("JwtServiceImpl::createToken execution ended.");
        return JWT.create()
                .withIssuer("tasks-api")
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }

    @Override
    public boolean validateToken(String token) {
        log.info("JwtServiceImpl::validateToken execution started.");
        try {
            Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
            JWT.require(algorithm)
                    .withIssuer("tasks-api")
                    .build()
                    .verify(token);
            log.info("JwtServiceImpl::validateToken execution ended.");
            return true;
        } catch (JWTVerificationException exception) {
            log.error("JwtServiceImpl::validateToken {}", exception.getMessage());
            return false;
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        log.info("JwtServiceImpl::getToken execution started.");
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;

        log.debug("JwtServiceImpl::getToken auth header provided: {}", auth);
        log.info("JwtServiceImpl::getToken execution ended.");
        return auth.substring(7);
    }

    @Override
    public UserModel getUserDetailsFromToken(String token) {
        log.info("JwtServiceImpl::getUserDetailsFromToken execution started.");
        Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("tasks-api")
                .build()
                .verify(token);

        String username = decodedJWT.getSubject();
        log.info("JwtServiceImpl::getUserDetailsFromToken execution ended.");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
