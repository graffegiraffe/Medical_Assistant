package by.rublevskaya.controller;

import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.dto.user.UserResponseDto;
import by.rublevskaya.dto.user.UserUpdateDto;
import by.rublevskaya.mapper.UserMapper;
import by.rublevskaya.model.User;
import by.rublevskaya.service.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserCrudController {

    private final UserCrudService userCrudService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserDto dto) {
        log.info("Received request to create user with data: {}", dto);
        User user = userCrudService.createUser(dto);
        UserResponseDto responseDto = userMapper.toDto(user);
        log.info("User created successfully: {}", responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("Fetching all users...");
        List<User> users = userCrudService.getAllUsers();
        log.info("Fetched {} users.", users.size());
        List<UserResponseDto> responseDtos = users.stream()
                .map(userMapper::toDto)
                .toList();
        log.info("Mapped users to response DTOs: {}", responseDtos);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userCrudService.getUserById(id);
        if (user != null) {
            UserResponseDto responseDto = userMapper.toDto(user);
            log.info("User with ID: {} retrieved successfully: {}", id, responseDto);
            return ResponseEntity.ok(responseDto);
        } else {
            log.warn("User with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        log.info("Received update request for user with ID: {} using data: {}", id, dto);
        User updatedUser = userCrudService.updateUser(id, dto);
        UserResponseDto responseDto = userMapper.toDto(updatedUser);
        log.info("User with ID: {} updated successfully: {}", id, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> partialUpdateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto) {
        log.info("Received partial update request for user with ID: {} using data: {}", id, dto);
        User updatedUser = userCrudService.partialUpdateUser(id, dto);
        UserResponseDto responseDto = userMapper.toDto(updatedUser);
        log.info("User with ID: {} partially updated successfully: {}", id, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("Received request to delete user with ID: {}", id);
        userCrudService.deleteUser(id);
        log.info("User with ID: {} deleted successfully", id);
        return ResponseEntity.ok("User deleted successfully");
    }
}