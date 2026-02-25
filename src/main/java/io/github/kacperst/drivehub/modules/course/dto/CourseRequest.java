package io.github.kacperst.drivehub.modules.course.dto;

import io.github.kacperst.drivehub.modules.fleet.model.LicenseCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseRequest {

    @NotNull(message = "Kategoria prawa jazdy jest wymagana")
    private LicenseCategory licenseCategory;

    @NotNull(message = "ID studenta jest wymagane")
    private UUID studentId;

    @NotNull(message = "ID instruktora jest wymagane")
    private UUID instructorId;

    @NotNull(message = "Liczba minut jest wymagana")
    @Min(value = 30, message = "Kurs musi trwać co najmniej 30 minut")
    private Integer totalMinutes;
}
