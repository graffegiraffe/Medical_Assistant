package by.rublevskaya.dto.clinic;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClinicUpdateDto {

    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    private String address;

    @Pattern(
            regexp = "^\\+?[0-9. ()-]{7,25}$",
            message = "Phone number has invalid format"
    )
    private String phone;
}
