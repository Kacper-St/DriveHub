package io.github.kacperst.drivehub.modules.course.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends BaseBusinessException {
    public CourseNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
