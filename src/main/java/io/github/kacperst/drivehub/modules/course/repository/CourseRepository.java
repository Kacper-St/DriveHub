package io.github.kacperst.drivehub.modules.course.repository;

import io.github.kacperst.drivehub.modules.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
}
