package com.jesusfs.tasks.domain.controller;

import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.ResponseUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.UpdateUserDTO;
import com.jesusfs.tasks.domain.service.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/profile")
@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUserDTO> getProfile(Authentication authentication) {
        log.info("UserController::getProfile execution started.");
        UserModel principal = (UserModel) authentication.getPrincipal();

        UserModel user = userService.getUserById(principal.getId());
        ResponseUserDTO responseDTO = new ResponseUserDTO(user.getUsername(), user.getEmail());
        log.info("UserController::getProfile execution ended.");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProfile(Authentication authentication, @RequestBody @Valid UpdateUserDTO userDTO) {
        log.info("UserController::updateProfile execution started.");
        UserModel principal = (UserModel) authentication.getPrincipal();

        log.debug("UserController::updateProfile params received: {}", userDTO);
        UserModel user = userService.updateUser(userDTO, principal.getId());
        ResponseUserDTO responseDTO = new ResponseUserDTO(user.getUsername(), user.getEmail());
        log.info("UserController::updateProfile execution ended.");
        return ResponseEntity.ok(responseDTO);
    }
}
