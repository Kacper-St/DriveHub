package io.github.kacperst.drivehub.modules.fleet.mapper;

import io.github.kacperst.drivehub.modules.fleet.dto.VehicleRequest;
import io.github.kacperst.drivehub.modules.fleet.dto.VehicleResponse;
import io.github.kacperst.drivehub.modules.fleet.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleResponse toResponse(Vehicle vehicle);
    List<VehicleResponse> toResponseList(List<Vehicle> vehicles);
    Vehicle toEntity(VehicleRequest vehicleRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateVehicle(VehicleRequest vehicleRequest, @MappingTarget Vehicle vehicle);
}
