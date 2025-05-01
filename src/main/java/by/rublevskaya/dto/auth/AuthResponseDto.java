package by.rublevskaya.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private String login;         // Логин пользователя
    private String role;          // Роль пользователя (например, ADMIN, DOCTOR, USER)
}