package io.github.kacperst.drivehub.modules.course.dto;

import io.github.kacperst.drivehub.modules.course.model.CourseStatus;
import io.github.kacperst.drivehub.modules.fleet.model.LicenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseResponse {

    private UUID id;
    private CourseStatus courseStatus;
    private LicenseCategory licenseCategory;
    private UUID studentId;
    private String student;
    private UUID instructorId;
    private String instructor;
    private Integer totalMinutes;
    private Integer remainingMinutes;
}
