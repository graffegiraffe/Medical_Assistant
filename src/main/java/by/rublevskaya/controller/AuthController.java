package by.rublevskaya.controller;

import by.rublevskaya.dto.auth.AuthRequestDto;
import by.rublevskaya.dto.auth.AuthResponseDto;
import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto dto) {
        log.info("Received request to register user with data: {}", dto);
        userService.registerUser(dto);
        log.info("User registration successful for: {}", dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto requestDto) {
        log.info("Received login request for user: {}", requestDto.getLogin());
        AuthResponseDto responseDto = userService.loginUser(requestDto);
        log.info("Login successful for user: {}", requestDto.getLogin());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register-doctor")
    public ResponseEntity<String> registerDoctor(@Valid @RequestBody DoctorDto dto) {
        log.info("Received request to register doctor with data: {}", dto);
        userService.registerDoctor(dto);
        log.info("Doctor registration successful for: {}", dto);
        return ResponseEntity.ok("Doctor registered successfully!");
    }
}