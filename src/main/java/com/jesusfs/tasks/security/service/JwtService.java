package com.jesusfs.tasks.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jesusfs.tasks.domain.model.auth.dto.RequestRefreshDTO;
import com.jesusfs.tasks.domain.model.auth.dto.ResponseTokenDTO;
import com.jesusfs.tasks.domain.model.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    ResponseTokenDTO createTokenDTO(RequestRefreshDTO tokenDTO);
    ResponseTokenDTO createTokenDTO(UserModel user);

    String createToken(UserDetails user);
    String createRefresh(UserDetails user);
    boolean validateToken(String token) throws JWTVerificationException;
    String getToken(HttpServletRequest request);
    UserDetails getUserDetailsFromToken(String token);
}
