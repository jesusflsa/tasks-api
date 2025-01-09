package com.jesusfs.tasks.domain.controller;

import com.jesusfs.tasks.domain.model.auth.dto.RequestRefreshDTO;
import com.jesusfs.tasks.domain.model.auth.dto.ResponseTokenDTO;
import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.LoginUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.RequestUserDTO;
import com.jesusfs.tasks.domain.service.user.UserService;
import com.jesusfs.tasks.security.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ResponseTokenDTO> registerUser(@RequestBody @Valid RequestUserDTO userDTO) {
        UserModel user = userService.saveUser(userDTO);
        ResponseTokenDTO tokenResponse = jwtService.createTokenDTO(user);
        return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDTO> loginUser(@RequestBody @Valid LoginUserDTO userDTO) {
        UserModel user = userService.loginUser(userDTO);
        ResponseTokenDTO tokenResponse = jwtService.createTokenDTO(user);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseTokenDTO> refreshToken(@RequestBody @Valid RequestRefreshDTO tokenDTO) {
        ResponseTokenDTO tokenResponse = jwtService.createTokenDTO(tokenDTO);
        return ResponseEntity.ok(tokenResponse);
    }
}
