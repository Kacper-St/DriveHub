package io.github.kacperst.drivehub.modules.fleet.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class VehicleNotFoundException extends BaseBusinessException {
    public VehicleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
