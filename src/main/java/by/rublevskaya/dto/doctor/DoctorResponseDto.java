package by.rublevskaya.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorResponseDto {
    private String specialty;
    private String licenseNumber;
    private String clinicName;
    private String fullName;
    private String username;
    private Boolean isActive;
}