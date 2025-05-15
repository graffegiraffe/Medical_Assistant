package by.rublevskaya.dto.healthmetric;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthMetricDto {

    @NotNull(message = "User ID cannot be null.")
    private Long userId;

    @NotNull(message = "Timestamp cannot be null.")
    private LocalDateTime timestamp;

    private String bloodPressure; // Формат: "120/80"

    private Double bloodSugar;

    private Double temperature;

    private String notes;
}