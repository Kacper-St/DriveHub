package io.github.kacperst.drivehub.modules.fleet.dto;

import io.github.kacperst.drivehub.modules.fleet.model.LicenseCategory;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VehicleRequest {

    @NotBlank(message = "VIN is required")
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    private String vin;

    @NotBlank(message = "Registration number is required")
    @Size(max = 15)
    private String registrationNumber;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Color is required")
    private String color;

    @NotNull(message = "Category is required")
    private LicenseCategory licenseCategory;

    @NotNull(message = "Insurance expiry date is required")
    @Future(message = "Insurance expiry date must be in the future")
    private LocalDate insuranceExpiryDate;

    @NotNull(message = "Insurance start date is required")
    @PastOrPresent(message = "Insurance start date cannot be in the future")
    private LocalDate insuranceStartDate;

    @NotNull(message = "Mileage is required")
    @PositiveOrZero(message = "Mileage cannot be negative")
    private Long mileage;

    @Size(max = 500)
    private String description;
}
