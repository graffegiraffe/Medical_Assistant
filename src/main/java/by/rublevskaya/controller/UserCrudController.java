package by.rublevskaya.controller;

import by.rublevskaya.dto.user.UserCreateDto;
import by.rublevskaya.dto.user.UserResponseDto;
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
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDto dto) {
        User user = userCrudService.createUser(dto);
        return ResponseEntity.ok(user);
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
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDto dto) {
        User updatedUser = userCrudService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userCrudService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}