package com.jesusfs.tasks.domain.service.user;

import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.RequestUserDTO;
import com.jesusfs.tasks.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserModel user;
    private UserModel user2;

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user = new UserModel();
        user2 = new UserModel();

        user.setId(1L);
        user.setUsername("jesusflsa");
        user.setEmail("jesusflsa@gmail.com");
        user.setPassword(bcrypt.encode("asd123"));
    }

    @Test
    void getUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserModel existingUser = userService.getUserById(1L);
        assertEquals("jesusflsa", existingUser.getUsername());
        assertEquals("jesusflsa@gmail.com", existingUser.getEmail());
    }

    @Test
    void saveUser() {
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        RequestUserDTO userDTO = new RequestUserDTO("jesusflsa", "jesusflsa@gmail.com", "asd123");
        UserModel savedUser = userService.saveUser(userDTO);

        assertNotNull(savedUser);
        assertEquals(userDTO.username(), savedUser.getUsername());
        assertEquals(userDTO.email(), savedUser.getEmail());
    }

    @Test
    void updateUser() {
    }
}