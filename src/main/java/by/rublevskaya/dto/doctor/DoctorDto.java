package by.rublevskaya.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDto {
    @NotBlank(message = "Specialty cannot be blank")
    @Size(max = 255, message = "Specialty must not exceed 255 characters")
    private String specialty;

    @NotBlank(message = "License number cannot be blank")
    @Size(max = 255, message = "License number must not exceed 255 characters")
    private String licenseNumber;

    @NotBlank(message = "Clinic name cannot be blank")
    @Size(max = 255, message = "Clinic name must not exceed 255 characters")
    private String clinicName;

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 150, message = "Full name must not exceed 150 characters")
    private String fullName;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotNull(message = "Active status must be set")
    private Boolean isActive;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Incorrect email format")
    private String email;


    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthDate;

    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "Blood type must be in the format A+, B-, AB-, O+, etc."
    )
    private String bloodType;
}
