package by.rublevskaya.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDto {

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    private LocalDate birthDate;

    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "Blood type must be in the format A+, B-, AB-, O+, etc."
    )
    private String bloodType;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

}