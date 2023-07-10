package com.ivannikov.project3.repositories;

import com.ivannikov.project3.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

}
