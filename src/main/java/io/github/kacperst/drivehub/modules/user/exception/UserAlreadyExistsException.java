package io.github.kacperst.drivehub.modules.user.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseBusinessException {

    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}