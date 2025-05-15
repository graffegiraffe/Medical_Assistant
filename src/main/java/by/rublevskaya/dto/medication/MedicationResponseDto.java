package by.rublevskaya.dto.medication;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicationResponseDto {
    private Long id;
    private Long userId;
    private String name;
    private String dosage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
}