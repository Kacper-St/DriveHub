package io.github.kacperst.drivehub.modules.user.service;

import io.github.kacperst.drivehub.common.exception.InternalTechnicalException;
import io.github.kacperst.drivehub.common.util.PasswordGenerator;
import io.github.kacperst.drivehub.modules.user.dto.LoginRequest;
import io.github.kacperst.drivehub.modules.user.dto.PasswordChangeRequest;
import io.github.kacperst.drivehub.modules.user.dto.UserRequest;
import io.github.kacperst.drivehub.modules.user.dto.UserResponse;
import io.github.kacperst.drivehub.modules.user.exception.InvalidCredentialsException;
import io.github.kacperst.drivehub.modules.user.exception.SamePasswordException;
import io.github.kacperst.drivehub.modules.user.exception.UserAlreadyExistsException;
import io.github.kacperst.drivehub.modules.user.exception.UserNotFoundException;
import io.github.kacperst.drivehub.modules.user.mapper.UserMapper;
import io.github.kacperst.drivehub.modules.user.model.Role;
import io.github.kacperst.drivehub.modules.user.model.User;
import io.github.kacperst.drivehub.modules.user.repository.RoleRepository;
import io.github.kacperst.drivehub.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PasswordGenerator passwordGenerator;

    @Override
    @Transactional(readOnly = true)
    public void loginUser(LoginRequest request) {
        log.info("Attempting authentication for identifier: {}", request.getLoginIdentifier());

        User user = userRepository.findByEmailOrPesel(request.getLoginIdentifier(), request.getLoginIdentifier())
                .orElseThrow(() -> {
                    log.warn("Authentication failed: User with identifier {} not found", request.getLoginIdentifier());
                    return new InvalidCredentialsException();
                });

        if (!user.isActive()) {
            log.warn("Authentication failed for identifier {}", request.getLoginIdentifier());
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Authentication failed: Invalid password for identifier {}", request.getLoginIdentifier());
            throw new InvalidCredentialsException();
        }

        if (user.isForcePasswordChange()) {
            log.info("Authentication successful for user {}. Redirection to password change required.", user.getPesel());
        } else {
            log.info("User {} successfully authenticated.", user.getPesel());
        }
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating new user with email: {} and PESEL: {}", userRequest.getEmail(), userRequest.getPesel());

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already taken");
        }
        if (userRepository.existsByPesel(userRequest.getPesel())) {
            throw new UserAlreadyExistsException("User with this PESEL already exists");
        }

        User user = userMapper.toEntity(userRequest);

        String rawPassword = passwordGenerator.generate(8);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setForcePasswordChange(true);
        user.setActive(true);

        Set<Role> roles = userRequest.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new InternalTechnicalException
                                ("Critical error: Role " + roleName + " not found")))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User savedUser = userRepository.saveAndFlush(user);
        log.info("User created successfully. TEMP PASSWORD for {}: {}", user.getPesel(), rawPassword);

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public void changePassword(PasswordChangeRequest request) {
        log.info("Attempting to change password for user: {}", request.getLoginIdentifier());

        User user = userRepository.findByEmailOrPesel(request.getLoginIdentifier(), request.getLoginIdentifier())
                .orElseThrow(() -> {
                    log.warn("Password change failed: User {} not found", request.getLoginIdentifier());
                    return new InvalidCredentialsException();
                });

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.warn("Password change failed: Current password does not match for user {}", request.getLoginIdentifier());
            throw new InvalidCredentialsException();
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            log.warn("Password change failed: New password is the same as the old one for user {}", request.getLoginIdentifier());
            throw new SamePasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setForcePasswordChange(false);

        userRepository.save(user);
        log.info("Password successfully changed for user: {}. ForcePasswordChange flag reset to false.", user.getPesel());
    }

    @Override
    @Transactional
    public void deleteUserById(UUID id) {
        log.info("Deactivating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Deactivation failed: User {} not found", id);
                    return new UserNotFoundException("User not found");
                });

        user.setActive(false);
        userRepository.save(user);

        log.info("User {} has been successfully deactivated", id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        log.info("Getting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    return new UserNotFoundException("User not found");
                });

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Getting all users");

        List<User> users = userRepository.findAllWithRoles();

        return userMapper.toResponse(users);
    }
}
