package io.github.kacperst.drivehub.modules.user.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class SamePasswordException extends BaseBusinessException {
    public SamePasswordException() {
        super("New password cannot be the same as the current one", HttpStatus.BAD_REQUEST);
    }
}
