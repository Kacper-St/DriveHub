package io.github.kacperst.drivehub.modules.fleet.controller;

import io.github.kacperst.drivehub.modules.fleet.dto.VehicleRequest;
import io.github.kacperst.drivehub.modules.fleet.dto.VehicleResponse;
import io.github.kacperst.drivehub.modules.fleet.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles() {
        log.info("REST request to get AllVehicle");
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @PostMapping
    public ResponseEntity<Void> createVehicle(@Valid @RequestBody VehicleRequest vehicleRequest) {
        log.info("REST request to save Vehicle : {}", vehicleRequest.getVin());

        UUID newVehicleId = vehicleService.addVehicle(vehicleRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newVehicleId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable UUID id) {
        log.info("REST request to get Vehicle : {}", id);
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        log.info("REST request to delete Vehicle : {}", id);
        vehicleService.deleteVehicleById(id);
        return ResponseEntity.noContent().build();
    }
}
