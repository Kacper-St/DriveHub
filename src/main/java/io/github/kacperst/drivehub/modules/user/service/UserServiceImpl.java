package io.github.kacperst.drivehub.modules.user.service;

import io.github.kacperst.drivehub.modules.user.dto.LoginRequest;
import io.github.kacperst.drivehub.modules.user.dto.RegisterRequest;
import io.github.kacperst.drivehub.modules.user.exception.InvalidCredentialsException;
import io.github.kacperst.drivehub.common.exception.InternalTechnicalException;
import io.github.kacperst.drivehub.modules.user.exception.UserAlreadyExistsException;
import io.github.kacperst.drivehub.modules.user.mapper.UserMapper;
import io.github.kacperst.drivehub.modules.user.model.Role;
import io.github.kacperst.drivehub.modules.user.model.RoleName;
import io.github.kacperst.drivehub.modules.user.model.User;
import io.github.kacperst.drivehub.modules.user.repository.RoleRepository;
import io.github.kacperst.drivehub.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void registerUser(RegisterRequest request) {
        log.info("Attempting to register new user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                .orElseThrow(() -> {
                    log.error("Critical error: ROLE_STUDENT not found in database!");
                    return new InternalTechnicalException("Role not found");
                });

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(studentRole));

        userRepository.save(user);
        log.info("User {} successfully registered", user.getEmail());
    }

    @Override
    public void loginUser(LoginRequest request) {
        log.info("Attempting to authenticate user: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Authentication failed: User with email {} not found", request.getEmail());
                    return new InvalidCredentialsException();
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            log.warn("Authentication failed: Wrong password for user {}", request.getEmail());
            throw new InvalidCredentialsException();
        }

        log.info("User {} successfully logged in", user.getEmail());

    }
}
