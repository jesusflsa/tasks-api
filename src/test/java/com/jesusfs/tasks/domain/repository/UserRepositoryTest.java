package com.jesusfs.tasks.domain.repository;

import com.jesusfs.tasks.domain.model.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private UserModel user;
    private UserModel user2;

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user = new UserModel();
        user2 = new UserModel();

        user.setUsername("jesusfs");
        user.setEmail("jesusfs@gmail.com");
        user.setPassword(bcrypt.encode("asd123"));
        user2.setPassword(bcrypt.encode("asd123"));
    }

    @Test
    @DisplayName("User can't be created with an exist username.")
    void existsByUsernameIgnoreCase() {
        userRepository.save(user);

        user2.setUsername("jesusfs");
        user2.setEmail("jesusflsa@gmail.com");

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
    }

    @Test
    @DisplayName("User can't be created with an exist email.")
    void existsByEmailIgnoreCase() {
        userRepository.save(user);

        user2.setUsername("jesusfsss");
        user2.setEmail("jesusfs@gmail.com");

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
    }
}