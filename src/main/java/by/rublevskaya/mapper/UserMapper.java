package by.rublevskaya.mapper;
import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.dto.user.UserResponseDto;
import by.rublevskaya.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {

    public User toEntity(UserDto dto) {
        log.info("Mapping UserDto to User entity: {}", dto);
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setBloodType(dto.getBloodType());
        user.setDoctorId(null);
        log.info("Mapped User entity: {}", user);
        return user;
    }

    public UserResponseDto toDto(User user) {
        log.info("Mapping User entity to UserResponseDto: {}", user);
        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setBloodType(user.getBloodType());
        log.info("Mapped UserResponseDto: {}", dto);
        return dto;
    }

}