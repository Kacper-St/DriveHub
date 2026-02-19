package io.github.kacperst.drivehub.modules.user.service;

import io.github.kacperst.drivehub.modules.user.dto.LoginRequest;
import io.github.kacperst.drivehub.modules.user.dto.RegisterRequest;

public interface UserService {
    void registerUser(RegisterRequest request);

    void loginUser(LoginRequest request);
}
