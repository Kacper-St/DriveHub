package io.github.kacperst.drivehub.modules.fleet.service;

import io.github.kacperst.drivehub.modules.fleet.dto.VehicleRequest;
import io.github.kacperst.drivehub.modules.fleet.dto.VehicleResponse;

import java.util.List;
import java.util.UUID;

public interface VehicleService {
    List<VehicleResponse> getAllVehicles();

    UUID createVehicle(VehicleRequest vehicleRequest);

    VehicleResponse getVehicleById(UUID id);

    void deleteVehicleById(UUID id);

    VehicleResponse updateVehicleById(UUID id, VehicleRequest vehicleRequest);
}
