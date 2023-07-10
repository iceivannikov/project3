package com.ivannikov.project3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value")
    @NotNull
    @Min(-100)
    @Max(100)
    private Double value;
    @Column(name = "raining")
    @NotNull
    private Boolean raining;
    @Column(name = "date_of_measurement")
    @NotNull
    private LocalDateTime dateOfMeasurement;
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @NotNull
    private Sensor sensor;

    public Boolean isRaining() {
        return raining;
    }
}
