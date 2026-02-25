package io.github.kacperst.drivehub.modules.course.mapper;

import io.github.kacperst.drivehub.modules.course.dto.CourseRequest;
import io.github.kacperst.drivehub.modules.course.dto.CourseResponse;
import io.github.kacperst.drivehub.modules.course.model.Course;
import io.github.kacperst.drivehub.modules.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(target = "student", source = "student", qualifiedByName = "mapUserToFullName")
    @Mapping(target = "instructor", source = "instructor", qualifiedByName = "mapUserToFullName")
    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponse(List<Course> courseList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "remainingMinutes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "courseStatus", ignore = true)
    Course toEntity(CourseRequest courseRequest);

    @Named("mapUserToFullName")
    default String mapUserToFullName(User user) {
        if  (user == null) {
            return null;
        }
        return user.getFirstName() + " " + user.getLastName();
    }
}
