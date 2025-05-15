package by.rublevskaya.dto.medicalrecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordDto {

    @NotNull(message = "User ID cannot be null.")
    private Long userId;

    @NotBlank(message = "Doctor license number cannot be empty.")
    private String licenseNumber;

    @NotBlank(message = "Title cannot be blank.")
    private String title;

    private LocalDate date;

    private String description;

    private String doctorNotes;

    @NotNull(message = "Type cannot be null. Allowed values: ALLERGY, VACCINE, ILLNESS.")
    private String type;
}
