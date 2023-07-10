package com.ivannikov.project3.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SensorDTO {
    @NotEmpty(message = "Field must not be empty")
    @Size(min = 3, max = 30, message = "Field must be from 3 to 30 characters")
    private String name;
}
