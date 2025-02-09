package com.jesusfs.tasks.domain.controller;

import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.ResponseUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.UpdateUserDTO;
import com.jesusfs.tasks.domain.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/profile")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Profile", description = "Profile endpoints")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseUserDTO> getProfile() {
        log.info("UserController::getProfile execution started.");

        UserModel user = userService.getUser();
        ResponseUserDTO responseDTO = new ResponseUserDTO(user.getUsername(), user.getEmail());
        log.info("UserController::getProfile execution ended.");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UpdateUserDTO userDTO) {
        log.info("UserController::updateProfile execution started.");
        log.debug("UserController::updateProfile params received: {}", userDTO);
        UserModel user = userService.updateUser(userDTO);
        ResponseUserDTO responseDTO = new ResponseUserDTO(user.getUsername(), user.getEmail());
        log.info("UserController::updateProfile execution ended.");
        return ResponseEntity.ok(responseDTO);
    }
}
