package io.github.kacperst.drivehub.modules.user.repository;

import io.github.kacperst.drivehub.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailOrPesel(String email, String pesel);
    boolean existsByPesel(String pesel);
    boolean existsByEmail(String email);
}
