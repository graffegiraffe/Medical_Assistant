package by.rublevskaya.dto.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponseDto {
    private String fullName;
    private String clinicName;
    private String specialty;
    private String licenseNumber;
    private LocalDateTime dateTime;
    private String notes;
    private Boolean completed;
}
