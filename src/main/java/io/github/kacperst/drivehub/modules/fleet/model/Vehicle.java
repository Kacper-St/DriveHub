package io.github.kacperst.drivehub.modules.fleet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "vehicles")
@Getter @Setter
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 17)
    @Size(min = 17, max = 17)
    private String vin;

    @NotBlank
    @Column(nullable = false, unique = true, length = 15)
    private String registrationNumber;

    @NotBlank
    @Column(nullable = false)
    private String brand;

    @NotBlank
    @Column(nullable = false)
    private String model;

    @NotBlank
    @Column(nullable = false)
    private String color;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenseCategory licenseCategory;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @NotNull
    @Future(message = "Insurance expiry date must be in the future")
    private LocalDate insuranceExpiryDate;

    @NotNull
    @PastOrPresent(message = "Insurance start date cannot be in the future")
    private LocalDate insuranceStartDate;

    @NotNull
    @PositiveOrZero(message = "Mileage cannot be negative")
    private Long mileage;

    @Size(max = 500)
    @Column(length = 500)
    private String description;
}
