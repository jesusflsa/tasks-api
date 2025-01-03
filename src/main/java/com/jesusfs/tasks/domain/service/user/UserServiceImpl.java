package com.jesusfs.tasks.domain.service.user;

import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.RequestUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.UpdateUserDTO;
import com.jesusfs.tasks.domain.repository.UserRepository;
import com.jesusfs.tasks.exceptions.UserNotValidException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel getUserById(Long id) {
        log.info("UserServiceImpl::getUserById execution started.");
        Optional<UserModel> opUser = userRepository.findById(id);
        log.info("UserServiceImpl::getUserById execution ended.");
        return opUser
                .orElseThrow(() -> new EntityNotFoundException("User with id \"" + id + "\" not found."));
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
        user.setEmail(user.getEmail());

        log.info("UserServiceImpl::saveUser execution ended.");
        return userRepository.save(user);
    }

    @Override
    public UserModel updateUser(@Valid UpdateUserDTO userDTO, Long id) {
        log.info("UserServiceImpl::updateUser execution ended.");
        validateUser(userDTO, id);

        log.debug("UserServiceImpl::updateUser params received for id {}: {}", id, userDTO);
        UserModel user = getUserById(id);
        user.setUsername(userDTO.username());
        user.setEmail(user.getEmail());

        log.info("UserServiceImpl::updateUser execution ended.");
        return userRepository.save(user);
    }

    @Override
    public void validateUser(RequestUserDTO userDTO) {
        log.info("UserServiceImpl::validateUser (RequestUserDTO) execution started.");
        List<ObjectError> exceptions = new ArrayList<>();
        if (userRepository.existsByUsernameIgnoreCase(userDTO.username())) {
            log.error("UserServiceImpl::validateUser (RequestUserDTO) username {} is taken.", userDTO.username());
            exceptions.add(new ObjectError("username", "This username is already in use."));
        }

        if (userRepository.existsByEmailIgnoreCase(userDTO.email())) {
            log.error("UserServiceImpl::validateUser (RequestUserDTO) email {} is taken.", userDTO.email());
            exceptions.add(new ObjectError("email", "This email is already in use."));
        }
        if (exceptions.isEmpty()) return;

        log.info("UserServiceImpl::validateUser (RequestUserDTO) execution ended.");
        throw new UserNotValidException(exceptions);
    }

    @Override
    public void validateUser(UpdateUserDTO userDTO, Long id) {
        log.info("UserServiceImpl::validateUser (UpdateUserDTO, Long) execution started.");
        List<ObjectError> exceptions = new ArrayList<>();
        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(userDTO.username(), id)) {
            log.error("UserServiceImpl::validateUser (UpdateUserDTO, Long) username {} for id {} is taken.", id, userDTO.username());
            exceptions.add(new ObjectError("username", "This username is already in use."));
        }

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(userDTO.email(), id)) {
            log.error("UserServiceImpl::validateUser (UpdateUserDTO, Long) email {} for id {} is taken.", id, userDTO.email());
            exceptions.add(new ObjectError("email", "This email is already in use."));
        }
        if (exceptions.isEmpty()) return;

        log.info("UserServiceImpl::validateUser (UpdateUserDTO, Long) execution ended.");
        throw new UserNotValidException(exceptions);
    }
}
