package com.jesusfs.tasks.security.filter;

import com.jesusfs.tasks.domain.model.user.UserModel;
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
        if (jwtService.validateToken(token)) {
            log.debug("JwtFilter::doFilterInternal token provided: {}.", token);
            UserModel user = jwtService.getUserDetailsFromToken(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        log.info("JwtFilter::doFilterInternal execution ended.");
        filterChain.doFilter(request, response);
    }
}
