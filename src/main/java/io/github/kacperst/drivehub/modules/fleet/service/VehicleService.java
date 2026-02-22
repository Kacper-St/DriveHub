package io.github.kacperst.drivehub.modules.fleet.service;

import io.github.kacperst.drivehub.modules.fleet.dto.VehicleRequest;
import io.github.kacperst.drivehub.modules.fleet.dto.VehicleResponse;

import java.util.List;
import java.util.UUID;

public interface VehicleService {
    List<VehicleResponse> getAllVehicles();

    UUID addVehicle(VehicleRequest vehicleRequest);
}
