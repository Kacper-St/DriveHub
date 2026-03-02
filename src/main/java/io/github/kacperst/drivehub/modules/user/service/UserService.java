package io.github.kacperst.drivehub.modules.user.service;

import io.github.kacperst.drivehub.modules.user.dto.LoginRequest;
import io.github.kacperst.drivehub.modules.user.dto.PasswordChangeRequest;
import io.github.kacperst.drivehub.modules.user.dto.UserRequest;
import io.github.kacperst.drivehub.modules.user.dto.UserResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface UserService {

    void loginUser(LoginRequest request);

    UserResponse createUser(UserRequest userRequest);

    void changePassword(@Valid PasswordChangeRequest request);

    void deleteUserById(UUID id);
}
