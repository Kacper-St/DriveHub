package io.github.kacperst.drivehub.modules.user.dto;

import io.github.kacperst.drivehub.modules.user.model.RoleName;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class UserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String pesel;
    private Set<RoleName> roles;
}
