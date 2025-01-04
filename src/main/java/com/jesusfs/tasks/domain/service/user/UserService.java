package com.jesusfs.tasks.domain.service.user;

import com.jesusfs.tasks.domain.model.user.UserModel;
import com.jesusfs.tasks.domain.model.user.dto.LoginUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.RequestUserDTO;
import com.jesusfs.tasks.domain.model.user.dto.UpdateUserDTO;
import jakarta.validation.Valid;

public interface UserService {
    UserModel getUser();
    UserModel saveUser(@Valid RequestUserDTO userDTO);
    UserModel updateUser(@Valid UpdateUserDTO userDTO);

    UserModel loginUser(@Valid LoginUserDTO userDTO);

    void validateUser(RequestUserDTO userDTO);
    void validateUser(UpdateUserDTO userDTO, Long id);
    void validateUser(LoginUserDTO userDTO);
}
