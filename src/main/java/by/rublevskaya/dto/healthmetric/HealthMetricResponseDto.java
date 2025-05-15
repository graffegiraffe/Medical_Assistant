package by.rublevskaya.dto.healthmetric;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthMetricResponseDto {
    private LocalDateTime timestamp;
    private String bloodPressure;
    private Double bloodSugar;
    private Double temperature;
    private String notes;
}