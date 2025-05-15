package by.rublevskaya.dto.medication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationResponseDto {
    private Long id;
    private Long userId;
    private String name;
    private String dosage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
}