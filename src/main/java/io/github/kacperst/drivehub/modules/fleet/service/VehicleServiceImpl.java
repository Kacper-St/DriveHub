package io.github.kacperst.drivehub.modules.fleet.service;

import io.github.kacperst.drivehub.modules.fleet.dto.VehicleRequest;
import io.github.kacperst.drivehub.modules.fleet.dto.VehicleResponse;
import io.github.kacperst.drivehub.modules.fleet.exception.VehicleAlreadyExistsException;
import io.github.kacperst.drivehub.modules.fleet.exception.VehicleNotFoundException;
import io.github.kacperst.drivehub.modules.fleet.mapper.VehicleMapper;
import io.github.kacperst.drivehub.modules.fleet.model.Vehicle;
import io.github.kacperst.drivehub.modules.fleet.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements  VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public List<VehicleResponse> getAllVehicles() {
        log.debug("Fetching all vehicles from database");
        List<Vehicle> vehicles = vehicleRepository.findAll();

        log.info("Retrieved {} vehicles", vehicles.size());
        return vehicleMapper.toResponseList(vehicles);
    }

    @Override
    @Transactional
    public UUID createVehicle(VehicleRequest vehicleRequest) {
        log.info("Adding vehicle with VIN: {}", vehicleRequest.getVin());

        if (vehicleRepository.existsByVin(vehicleRequest.getVin())){
            log.warn("Vehicle with VIN {} already exists", vehicleRequest.getVin());
            throw new VehicleAlreadyExistsException(vehicleRequest.getVin());
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequest);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        log.info("Vehicle successfully saved with ID: {}", savedVehicle.getId());

        return savedVehicle.getId();
    }

    @Override
    public VehicleResponse getVehicleById(UUID id) {
        log.info("Retrieving vehicle with ID: {}", id);

        Vehicle vehicle = findVehicleOrThrow(id);

        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicleById(UUID id) {
        log.info("Deleting vehicle with ID: {}", id);

        Vehicle vehicle = findVehicleOrThrow(id);

        vehicleRepository.delete(vehicle);
    }

    @Override
    @Transactional
    public VehicleResponse updateVehicleById(UUID id, VehicleRequest vehicleRequest) {
        log.info("Updating vehicle with ID: {}", id);

        Vehicle vehicle = findVehicleOrThrow(id);

        if (!vehicle.getVin().equals(vehicleRequest.getVin())) {
            if (vehicleRepository.existsByVinAndIdNot(vehicleRequest.getVin(), id)) {
                log.warn("Cannot update vehicle. VIN {} already exists in another record", vehicleRequest.getVin());
                throw new VehicleAlreadyExistsException(vehicleRequest.getVin());
            }
        }

        vehicleMapper.updateVehicle(vehicleRequest, vehicle);

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        log.info("Vehicle successfully updated with ID: {}", updatedVehicle.getId());

        return vehicleMapper.toResponse(updatedVehicle);
    }

    private Vehicle findVehicleOrThrow(UUID id){
        return vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Vehicle with ID {} not found", id);
                    return new VehicleNotFoundException("Vehicle not found with ID: " + id);
                });
    }
}
