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

    @Size(min = 3, max = 20, message = "Имя пользователя должно быть от 3 до 20 символов")
    private String username;

    @Email(message = "Некорректный формат email")
    private String email;

    private LocalDate birthDate; // Позволим null, если не обновляется

    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "Группа крови должна быть в формате A+, B-, AB-, O+ и т.д."
    )
    private String bloodType;

}