package com.ivannikov.project3.controllers;

import com.ivannikov.project3.dto.MeasurementDTO;
import com.ivannikov.project3.exceptions.SensorNotCreatedException;
import com.ivannikov.project3.models.Measurement;
import com.ivannikov.project3.dto.MeasurementsResponse;
import com.ivannikov.project3.services.MeasurementService;
import com.ivannikov.project3.util.ErrorsUtil;
import com.ivannikov.project3.util.MeasurementValidator;
import com.ivannikov.project3.util.SensorErrorResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper mapper;
    private final MeasurementValidator validator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper mapper, MeasurementValidator validator) {
        this.measurementService = measurementService;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {

        Measurement measurement = convertToMeasurement(measurementDTO);
        validator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorsUtil.ErrorMessage(bindingResult);
        }
        measurementService.addMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException ex) {
        SensorErrorResponse response = new SensorErrorResponse(
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return mapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return mapper.map(measurement, MeasurementDTO.class);
    }
}
