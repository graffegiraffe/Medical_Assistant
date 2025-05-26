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

    @NotNull(message = "Blood Pressure cannot be null.")
    private String bloodPressure; // Формат: "120/80"

    @NotNull(message = "Blood Sugar cannot be null.")
    private Double bloodSugar;

    @NotNull(message = "Temperature cannot be null.")
    private Double temperature;

    @NotNull(message = "Notes cannot be null.")
    private String notes;
}