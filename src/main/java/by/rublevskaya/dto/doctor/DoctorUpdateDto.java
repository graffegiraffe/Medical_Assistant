package by.rublevskaya.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorUpdateDto {

    @Size(max = 255, message = "Specialty must not exceed 255 characters")
    private String specialty;

    @Size(max = 255, message = "License number must not exceed 255 characters")
    private String licenseNumber;

    @Size(max = 255, message = "Clinic name must not exceed 255 characters")
    private String clinicName;

    @Size(max = 150, message = "Full name must not exceed 150 characters")
    private String fullName;

    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    private Boolean isActive;

    @Email(message = "Incorrect email format")
    private String email;

    @Past(message = "Birthdate must be in the past")
    private LocalDate birthDate;

    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "Blood type must be in the format A+, B-, AB-, O+, etc."
    )
    private String bloodType;
}
