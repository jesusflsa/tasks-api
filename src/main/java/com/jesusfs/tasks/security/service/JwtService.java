package com.jesusfs.tasks.security.service;

import com.jesusfs.tasks.domain.model.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String createToken(UserModel user);
    boolean validateToken(String token);
    String getToken(HttpServletRequest request);
    UserDetails getUserDetailsFromToken(String token);
}
