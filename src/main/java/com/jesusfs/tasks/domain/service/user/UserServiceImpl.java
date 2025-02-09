package com.jesusfs.tasks.domain.service.user;

import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.LoginUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.RequestUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.UpdateUserDTO;
import com.jesusfs.tasks.domain.repository.UserRepository;
import com.jesusfs.tasks.exceptions.UserNotFoundException;
import com.jesusfs.tasks.exceptions.UserNotValidException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    @Override
    public UserModel getUser() {
        log.info("UserServiceImpl::getUser execution started.");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        UserModel user = userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> {
            log.error("Invalid user: {}", principal);
            return new UserNotFoundException("Invalid user. Login first");
        });
        log.debug("UserServiceImpl::getUser user got: {}", user);
        log.info("UserServiceImpl::getUser execution ended.");
        return user;
    }

    @Override
    public UserModel saveUser(@Valid RequestUserDTO userDTO) {
        log.info("UserServiceImpl::saveUser execution started.");
        validateUser(userDTO);

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        log.debug("UserServiceImpl::saveUser params received: {}", userDTO);
        UserModel user = new UserModel();
        user.setUsername(userDTO.username());
        user.setPassword(bcrypt.encode(userDTO.password()));
        user.setEmail(userDTO.email());

        log.info("UserServiceImpl::saveUser execution ended.");
        return userRepository.save(user);
    }

    @Override
    public UserModel updateUser(@Valid UpdateUserDTO userDTO) {
        log.info("UserServiceImpl::updateUser execution ended.");
        UserModel user = this.getUser();
        validateUser(userDTO, user.getId());

        log.debug("UserServiceImpl::updateUser params received for id {}: {}", user.getId(), userDTO);
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());

        log.info("UserServiceImpl::updateUser execution ended.");
        return userRepository.save(user);
    }

    @Override
    public UserModel loginUser(LoginUserDTO userDTO) {
        log.info("UserServiceImpl::loginUser execution started.");
        validateUser(userDTO);

        log.debug("UserServiceImpl::loginUser params received: {}",userDTO);
        UserModel user = userRepository.findByUsername(userDTO.username()).get();

        boolean valid = bCrypt.matches(userDTO.password(), user.getPassword());
        if (!valid) {
            log.error("UserServiceImpl::loginUser password for {} is incorrect.", userDTO.username());
            throw new UserNotValidException(List.of(new FieldError("", "password", "Password is incorrect")));
        }

        log.info("UserServiceImpl::loginUser execution ended.");
        return user;
    }

    @Override
    public void validateUser(RequestUserDTO userDTO) {
        log.info("UserServiceImpl::validateUser (RequestUserDTO) execution started.");
        List<FieldError> exceptions = new ArrayList<>();
        if (userRepository.existsByUsernameIgnoreCase(userDTO.username())) {
            log.error("UserServiceImpl::validateUser (RequestUserDTO) username {} is taken.", userDTO.username());
            exceptions.add(new FieldError("", "username", "This username is already in use."));
        }

        if (userRepository.existsByEmailIgnoreCase(userDTO.email())) {
            log.error("UserServiceImpl::validateUser (RequestUserDTO) email {} is taken.", userDTO.email());
            exceptions.add(new FieldError("", "email", "This email is already in use."));
        }
        if (exceptions.isEmpty()) return;

        log.info("UserServiceImpl::validateUser (RequestUserDTO) execution ended.");
        throw new UserNotValidException(exceptions);
    }

    @Override
    public void validateUser(UpdateUserDTO userDTO, Long id) {
        log.info("UserServiceImpl::validateUser (UpdateUserDTO, Long) execution started.");
        List<FieldError> exceptions = new ArrayList<>();
        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(userDTO.username(), id)) {
            log.error("UserServiceImpl::validateUser (UpdateUserDTO, Long) username {} for id {} is taken.", userDTO.username(), id);
            exceptions.add(new FieldError("", "username", "This username is already in use."));
        }

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(userDTO.email(), id)) {
            log.error("UserServiceImpl::validateUser (UpdateUserDTO, Long) email {} for id {} is taken.", userDTO.email(), id);
            exceptions.add(new FieldError("","email", "This email is already in use."));
        }
        if (exceptions.isEmpty()) return;

        log.info("UserServiceImpl::validateUser (UpdateUserDTO, Long) execution ended.");
        throw new UserNotValidException(exceptions);
    }

    @Override
    public void validateUser(LoginUserDTO userDTO) {
        log.info("UserServiceImpl::validateUser (LoginUserDTO) execution started.");
        List<FieldError> exceptions = new ArrayList<>();
        if (!userRepository.existsByUsernameIgnoreCase(userDTO.username())) {
            log.error("UserServiceImpl::validateUser (LoginUserDTO) username {} not exists.", userDTO.username());
            exceptions.add(new FieldError("","username", "This username not exists."));
        }

        if (exceptions.isEmpty()) return;

        log.info("UserServiceImpl::validateUser (LoginUserDTO) execution ended.");
        throw new UserNotValidException(exceptions);
    }
}
