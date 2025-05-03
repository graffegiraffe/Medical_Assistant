package by.rublevskaya.dto.user;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String email;
    private LocalDate birthDate;
    private String bloodType;
    private String fullName;
}