package by.rublevskaya.dto.appointment;

import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentUpdateDto {

    private String notes;

    @Future(message = "Date and time of the appointment must be in the future")
    private LocalDateTime dateTime;
}