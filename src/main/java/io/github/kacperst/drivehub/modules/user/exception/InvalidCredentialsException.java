package io.github.kacperst.drivehub.modules.user.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BaseBusinessException {

    public InvalidCredentialsException() {
        super("Invalid password or email", HttpStatus.UNAUTHORIZED);
    }
}

