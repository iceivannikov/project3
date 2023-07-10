package com.ivannikov.project3.util;

import com.ivannikov.project3.models.Sensor;
import com.ivannikov.project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    public static final String ERROR_MESSAGE = "Sensor with the same name already exists";
    public static final String SENSOR_NAME = "name";
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue(SENSOR_NAME, ERROR_MESSAGE);
        }
    }
}
