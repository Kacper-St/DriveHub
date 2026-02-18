package io.github.kacperst.drivehub.modules.user.service;

import io.github.kacperst.drivehub.modules.user.dto.RegisterRequest;
import io.github.kacperst.drivehub.modules.user.model.AuthProvider;
import io.github.kacperst.drivehub.modules.user.model.Role;
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

    @Override
    @Transactional
    public void registerUser(RegisterRequest request) {
        log.info("Attempting to register new user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new RuntimeException("User with this email already exists");
        }

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> {
                    log.error("Critical error: ROLE_STUDENT not found in database!");
                    return new RuntimeException("Default role not found");
                });

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setRoles(Collections.singleton(studentRole));
        user.setActive(true);

        userRepository.save(user);
        log.info("User {} successfully registered", user.getEmail());
    }
}
