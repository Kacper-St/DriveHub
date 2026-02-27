package io.github.kacperst.drivehub.modules.course.controller;

import io.github.kacperst.drivehub.modules.course.dto.CourseRequest;
import io.github.kacperst.drivehub.modules.course.dto.CourseResponse;
import io.github.kacperst.drivehub.modules.course.service.CourseService;
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
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        log.debug("Attempting to fetch all courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest courseRequest){
        log.info("Attempting to create course for student ID: {}", courseRequest.getStudentId());

        CourseResponse createdCourse = courseService.createCourse(courseRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourse.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable UUID id){
        log.debug("Attempting to fetch course for course ID: {}", id);
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable UUID id){
        log.debug("Attempting to delete course ID: {}", id);
        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }
}
