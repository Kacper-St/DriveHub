package io.github.kacperst.drivehub.modules.course.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidUserRoleException extends BaseBusinessException {
    public InvalidUserRoleException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
