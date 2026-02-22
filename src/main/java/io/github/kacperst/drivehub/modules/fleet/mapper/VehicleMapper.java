package io.github.kacperst.drivehub.modules.fleet.mapper;

import io.github.kacperst.drivehub.modules.fleet.dto.VehicleRequest;
import io.github.kacperst.drivehub.modules.fleet.dto.VehicleResponse;
import io.github.kacperst.drivehub.modules.fleet.model.Vehicle;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleResponse toResponse(Vehicle vehicle);
    List<VehicleResponse> toResponseList(List<Vehicle> vehicles);
    Vehicle toEntity(VehicleRequest vehicleRequest);
}
