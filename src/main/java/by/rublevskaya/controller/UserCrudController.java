package by.rublevskaya.controller;

import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.dto.user.UserResponseDto;
import by.rublevskaya.dto.user.UserUpdateDto;
import by.rublevskaya.mapper.UserMapper;
import by.rublevskaya.model.User;
import by.rublevskaya.service.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCrudController {

    private final UserCrudService userCrudService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserDto dto) {
        User user = userCrudService.createUser(dto);
        UserResponseDto responseDto = userMapper.toDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userCrudService.getAllUsers();
        System.out.println("Fetched users: " + users);
        List<UserResponseDto> responseDtos = users.stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userCrudService.getUserById(id);
        UserResponseDto responseDto = userMapper.toDto(user);
        return ResponseEntity.ok(responseDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        User updatedUser = userCrudService.updateUser(id, dto);
        UserResponseDto responseDto = userMapper.toDto(updatedUser);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> partialUpdateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto) {
        User updatedUser = userCrudService.partialUpdateUser(id, dto);
        UserResponseDto responseDto = userMapper.toDto(updatedUser);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userCrudService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}