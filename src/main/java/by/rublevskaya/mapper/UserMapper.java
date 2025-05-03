package by.rublevskaya.mapper;

import by.rublevskaya.dto.auth.UserRegistrationDto;
import by.rublevskaya.dto.user.UserResponseDto;
import by.rublevskaya.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setBloodType(dto.getBloodType());
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setBloodType(user.getBloodType());
        return dto;
    }

}