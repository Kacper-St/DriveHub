CREATE TABLE vehicles (
                          id UUID NOT NULL,
                          brand VARCHAR(255) NOT NULL,
                          model VARCHAR(255) NOT NULL,
                          color VARCHAR(255) NOT NULL,
                          vin VARCHAR(17) NOT NULL,
                          registration_number VARCHAR(15) NOT NULL,
                          category VARCHAR(20) NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          mileage BIGINT NOT NULL,
                          insurance_start_date DATE NOT NULL,
                          insurance_expiry_date DATE NOT NULL,
                          description VARCHAR(500),
                          created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
                          updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,

                          CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

ALTER TABLE vehicles ADD CONSTRAINT uk_vehicles_vin UNIQUE (vin);
ALTER TABLE vehicles ADD CONSTRAINT uk_vehicles_registration_number UNIQUE (registration_number);

ALTER TABLE vehicles ADD CONSTRAINT chk_vehicles_status
    CHECK (status IN ('AVAILABLE', 'IN_REPAIR', 'INSPECTION_REQUIRED', 'OUT_OF_SERVICE'));

ALTER TABLE vehicles ADD CONSTRAINT chk_vehicles_category
    CHECK (category IN ('AM', 'A1', 'A2', 'A', 'B1', 'B', 'BE', 'C1', 'C', 'C1E', 'CE', 'D1', 'D', 'D1E', 'DE'));

CREATE INDEX idx_vehicles_status_category ON vehicles(status, category);