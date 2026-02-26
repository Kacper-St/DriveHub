package io.github.kacperst.drivehub.modules.course.repository;

import io.github.kacperst.drivehub.modules.course.model.Course;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"student", "instructor"})
    List<Course> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"student", "instructor"})
    Optional<Course> findById(@NonNull UUID id);
}
