package com.ivannikov.project3.util;

import com.ivannikov.project3.models.Measurement;
import com.ivannikov.project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class MeasurementValidator implements Validator {

    public static final String ERROR_MESSAGE = "Sensor with this name does not exist";
    public static final String SENSOR = "sensor";
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (Objects.isNull(measurement.getSensor())) {
            return;
        }
        if (sensorService.findByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue(SENSOR, ERROR_MESSAGE);
        }
    }
}
