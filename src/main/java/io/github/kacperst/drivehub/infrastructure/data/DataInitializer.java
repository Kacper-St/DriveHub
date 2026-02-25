package io.github.kacperst.drivehub.infrastructure.data;

import io.github.kacperst.drivehub.modules.user.model.Role;
import io.github.kacperst.drivehub.modules.user.model.RoleName;
import io.github.kacperst.drivehub.modules.user.model.User;
import io.github.kacperst.drivehub.modules.user.repository.RoleRepository;
import io.github.kacperst.drivehub.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Baza danych zawiera już dane. Pomijam inicjalizację danych startowych.");
            return;
        }

        log.info("Rozpoczynam inicjalizację danych startowych (Data Seeding)...");

        Role adminRole = getOrCreateRole(RoleName.ROLE_ADMIN);
        Role instructorRole = getOrCreateRole(RoleName.ROLE_INSTRUCTOR);
        Role studentRole = getOrCreateRole(RoleName.ROLE_STUDENT);

        saveUser("admin@drivehub.pl", "Adam", "Adminowski", Set.of(adminRole));
        saveUser("instruktor@drivehub.pl", "Jan", "Kowalski", Set.of(instructorRole));
        saveUser("kursant@drivehub.pl", "Kacper", "Kursant", Set.of(studentRole));

        log.info("Inicjalizacja danych zakończona pomyślnie.");
    }

    private Role getOrCreateRole(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    log.info("Tworzenie brakującej roli: {}", roleName);
                    return roleRepository.save(new Role(roleName));
                });
    }

    private void saveUser(String email, String firstName, String lastName, Set<Role> roles) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword("password123");
        user.setRoles(roles);
        userRepository.save(user);
        log.info("Zapisano użytkownika testowego: {}", email);
    }
}