package com.jesusfs.tasks.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jesusfs.tasks.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtFilter::doFilterInternal execution started.");
        String token = jwtService.getToken(request);
        try {
            if (token != null && jwtService.validateToken(token)) {
                log.debug("JwtFilter::doFilterInternal token provided: {}.", token);
                UserDetails user = jwtService.getUserDetailsFromToken(token);
                Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JWTVerificationException e) {
            log.error("JwtFilter::doFilterInternal An error has occurred: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
        filterChain.doFilter(request, response);
        log.info("JwtFilter::doFilterInternal execution ended.");
    }
}
