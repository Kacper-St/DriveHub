package io.github.kacperst.drivehub.modules.fleet.exception;

import io.github.kacperst.drivehub.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class VehicleAlreadyExistsException extends BaseBusinessException {

    public VehicleAlreadyExistsException(String vin) {
        super(String.format("Vehicle with Vin: %s already exists", vin),
                HttpStatus.BAD_REQUEST);
    }
}
