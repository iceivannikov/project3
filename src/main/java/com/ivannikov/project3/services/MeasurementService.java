package com.ivannikov.project3.services;

import com.ivannikov.project3.models.Measurement;
import com.ivannikov.project3.models.Sensor;
import com.ivannikov.project3.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        Sensor sensor = null;
        Optional<Sensor> optionalSensor = sensorService.findByName(measurement.getSensor().getName());
        if (optionalSensor.isPresent()) {
            sensor = optionalSensor.get();
        }
        measurement.setSensor(sensor);
        measurement.setDateOfMeasurement(LocalDateTime.now());
    }
}
