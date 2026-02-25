package io.github.kacperst.drivehub.modules.course.service;

import io.github.kacperst.drivehub.modules.course.dto.CourseRequest;
import io.github.kacperst.drivehub.modules.course.dto.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<CourseResponse> getAllCourses();

    CourseResponse createCourse(CourseRequest courseRequest);
}
