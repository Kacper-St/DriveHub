package io.github.kacperst.drivehub.modules.user.service;

import io.github.kacperst.drivehub.modules.user.dto.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void loginUser(LoginRequest request);

    UserResponse createUser(UserRequest userRequest);

    void changePassword(@Valid PasswordChangeRequest request);

    void deleteUserById(UUID id);

    UserResponse getUserById(UUID id);

    List<UserResponse> getAllUsers();

    UserResponse updateUserById(UUID id, @Valid UserRequest userRequest);
}
