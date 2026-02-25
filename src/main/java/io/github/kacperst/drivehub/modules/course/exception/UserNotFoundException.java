package io.github.kacperst.drivehub.modules.course.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseBusinessException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

