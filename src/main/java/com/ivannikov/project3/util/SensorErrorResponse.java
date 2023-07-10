package com.ivannikov.project3.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class SensorErrorResponse {
    private String message;
    private LocalDateTime time;
}
