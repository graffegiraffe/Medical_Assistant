package by.rublevskaya.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDto {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Doctor license number cannot be null or blank")
    private String licenseNumber;

    @Future(message = "Date and time of the appointment must be in the future")
    @NotNull(message = "Date and time cannot be null")
    private LocalDateTime dateTime;

    private String notes;
}