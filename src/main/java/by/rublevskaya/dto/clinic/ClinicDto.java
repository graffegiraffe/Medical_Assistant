package by.rublevskaya.dto.clinic;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class ClinicDto {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "^\\+?[0-9. ()-]{7,25}$",
            message = "Phone number has invalid format"
    )
    private String phone;
}