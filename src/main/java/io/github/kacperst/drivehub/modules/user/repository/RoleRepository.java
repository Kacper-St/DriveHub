package io.github.kacperst.drivehub.modules.user.repository;

import io.github.kacperst.drivehub.modules.user.model.Role;
import io.github.kacperst.drivehub.modules.user.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleName name);
}
