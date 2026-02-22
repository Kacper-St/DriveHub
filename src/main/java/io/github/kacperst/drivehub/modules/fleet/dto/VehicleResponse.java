package io.github.kacperst.drivehub.modules.fleet.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class VehicleResponse {

    private UUID id;
    private String vin;
    private String registrationNumber;
    private String brand;
    private String model;
    private String color;
    private String licenseCategory;
    private String status;
    private LocalDate insuranceExpiryDate;
    private Long mileage;
}
