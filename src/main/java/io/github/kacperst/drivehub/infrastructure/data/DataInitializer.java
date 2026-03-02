package io.github.kacperst.drivehub.infrastructure.data;

import io.github.kacperst.drivehub.common.util.PasswordGenerator;
import io.github.kacperst.drivehub.modules.user.model.Role;
import io.github.kacperst.drivehub.modules.user.model.RoleName;
import io.github.kacperst.drivehub.modules.user.model.User;
import io.github.kacperst.drivehub.modules.user.repository.RoleRepository;
import io.github.kacperst.drivehub.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Baza danych zawiera już dane. Pomijam inicjalizację.");
            return;
        }

        log.info("Rozpoczynam Data Seeding...");

        Role adminRole = getOrCreateRole(RoleName.ROLE_ADMIN);
        Role instructorRole = getOrCreateRole(RoleName.ROLE_INSTRUCTOR);
        Role studentRole = getOrCreateRole(RoleName.ROLE_STUDENT);

        saveUser("admin@drivehub.pl", "Adam", "Adminowski", "90010100001", Set.of(adminRole));
        saveUser("instruktor@drivehub.pl", "Jan", "Kowalski", "85020211112", Set.of(instructorRole));
        saveUser("kursant@drivehub.pl", "Kacper", "Kursant", "02030322223", Set.of(studentRole));

        log.info("Inicjalizacja zakończona. Hasła wyświetlone powyżej w logach.");
    }

    private void saveUser(String email, String firstName, String lastName, String pesel, Set<Role> roles) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPesel(pesel);

        String tempPassword = passwordGenerator.generate(8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setForcePasswordChange(true);
        user.setRoles(roles);

        userRepository.save(user);

        log.info("UTWORZONO: {} | PESEL: {} | HASŁO: {}", email, pesel, tempPassword);
    }

    private Role getOrCreateRole(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}