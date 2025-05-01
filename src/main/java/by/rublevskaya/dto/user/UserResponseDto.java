package by.rublevskaya.dto.user;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;             // Имя пользователя
    private String email;                // Email
    private LocalDate birthDate;         // Дата рождения
    private String bloodType;            // Группа крови
    private String fullName;             // Полное имя (если используется)

}