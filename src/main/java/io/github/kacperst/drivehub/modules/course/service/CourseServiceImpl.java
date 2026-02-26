package io.github.kacperst.drivehub.modules.course.service;

import io.github.kacperst.drivehub.modules.course.dto.CourseRequest;
import io.github.kacperst.drivehub.modules.course.dto.CourseResponse;
import io.github.kacperst.drivehub.modules.course.exception.CourseNotFoundException;
import io.github.kacperst.drivehub.modules.course.exception.InvalidUserRoleException;
import io.github.kacperst.drivehub.modules.course.exception.UserNotFoundException;
import io.github.kacperst.drivehub.modules.course.mapper.CourseMapper;
import io.github.kacperst.drivehub.modules.course.model.Course;
import io.github.kacperst.drivehub.modules.course.repository.CourseRepository;
import io.github.kacperst.drivehub.modules.user.model.RoleName;
import io.github.kacperst.drivehub.modules.user.model.User;
import io.github.kacperst.drivehub.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;

    @Override
    public List<CourseResponse> getAllCourses() {
        log.debug("Fetching all courses");

        List<Course> courseList = courseRepository.findAll();

        log.debug("Courses fetched");
        return courseMapper.toResponse(courseList);
    }

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest courseRequest) {
        log.info("Creating course for student ID: {}", courseRequest.getStudentId());

        User student = userRepository.findById(courseRequest.getStudentId())
                .orElseThrow(() ->
                        new UserNotFoundException("Student with ID: "
                                + courseRequest.getStudentId()
                                + " not found"));

        if (!student.hasRole(RoleName.ROLE_STUDENT)) {
            throw new InvalidUserRoleException("User " + student.getId() + " is not a registered student");
        }

        User instructor = userRepository.findById(courseRequest.getInstructorId())
                .orElseThrow(() ->
                        new UserNotFoundException("Instructor with id :"
                                + courseRequest.getInstructorId()
                                + " not found"));

        if (!instructor.hasRole(RoleName.ROLE_INSTRUCTOR)) {
            throw new InvalidUserRoleException("User " + instructor.getId() + " is not a registered instructor");
        }

        Course course = courseMapper.toEntity(courseRequest);
        course.setInstructor(instructor);
        course.setStudent(student);
        course.setRemainingMinutes(course.getTotalMinutes());

        Course savedCourse = courseRepository.save(course);

        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public CourseResponse getCourseById(UUID id) {
        log.debug("Fetching course ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new CourseNotFoundException("Course with ID: " + id + " not found"));

        return courseMapper.toResponse(course);
    }
}
