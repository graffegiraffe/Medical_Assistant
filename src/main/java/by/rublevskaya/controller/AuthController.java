package by.rublevskaya.controller;

import by.rublevskaya.dto.auth.UserRegistrationDto;
import by.rublevskaya.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully!");
    }

}