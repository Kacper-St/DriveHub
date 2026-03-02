package io.github.kacperst.drivehub.modules.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class UserResponse {
    private UUID id;
    private String email;
    private String pesel;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private boolean forcePasswordChange;
    private Set<String> roles;
    private Instant createdAt;
}
