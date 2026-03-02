package io.github.kacperst.drivehub.modules.user.controller;

import io.github.kacperst.drivehub.modules.user.dto.LoginRequest;
import io.github.kacperst.drivehub.modules.user.dto.PasswordChangeRequest;
import io.github.kacperst.drivehub.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        userService.loginUser(request);
        return ResponseEntity.ok("Zalogowano pomyślnie");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Password changed successfully. You can now log in with your new credentials.");
    }
}