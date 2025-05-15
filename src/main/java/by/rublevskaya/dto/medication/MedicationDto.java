package by.rublevskaya.dto.medication;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;


@Data

public class MedicationDto {

    @NotNull(message = "User ID cannot be null.")
    private Long userId;

    @NotBlank(message = "Medication name is required.")
    @Size(min = 2, max = 100, message = "Medication name must be between 2 and 100 characters.")
    private String name;

    @Size(max = 50, message = "Dosage must not exceed 50 characters.")
    private String dosage;

    @PastOrPresent(message = "Start date cannot be in the future.")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "End date must be in the future or present.")
    private LocalDateTime endDate;

    @AssertTrue(message = "End date must be after start date.")
    private boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }

    private Boolean active;
}
