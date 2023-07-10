package com.ivannikov.project3.controllers;

import com.ivannikov.project3.dto.SensorDTO;
import com.ivannikov.project3.exceptions.SensorNotCreatedException;
import com.ivannikov.project3.models.Sensor;
import com.ivannikov.project3.services.SensorService;
import com.ivannikov.project3.util.ErrorsUtil;
import com.ivannikov.project3.util.SensorErrorResponse;
import com.ivannikov.project3.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper mapper;
    private final SensorValidator validator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper mapper, SensorValidator validator) {
        this.sensorService = sensorService;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping("/registration")
    public HttpEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {

        Sensor sensor = convertToSensor(sensorDTO);

        validator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorsUtil.ErrorMessage(bindingResult);
        }

        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException ex) {
        SensorErrorResponse response = new SensorErrorResponse(
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return mapper.map(sensorDTO, Sensor.class);
    }
}
