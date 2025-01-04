package com.jesusfs.tasks.security.service;

import com.jesusfs.tasks.domain.model.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String createToken(UserModel user);
    boolean validateToken(String token);
    String getToken(HttpServletRequest request);
    UserModel getUserDetailsFromToken(String token);
}
