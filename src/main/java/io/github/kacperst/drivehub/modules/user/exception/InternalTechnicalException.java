package io.github.kacperst.drivehub.modules.user.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class InternalTechnicalException extends BaseBusinessException {

    public InternalTechnicalException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
