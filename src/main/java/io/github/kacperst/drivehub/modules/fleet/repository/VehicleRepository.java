package io.github.kacperst.drivehub.modules.fleet.repository;

import io.github.kacperst.drivehub.modules.fleet.model.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    boolean existsByVin(String vin);

    boolean existsByVinAndIdNot(String vin, UUID id);
}
