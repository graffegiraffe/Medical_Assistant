package by.rublevskaya.controller;

import by.rublevskaya.dto.auth.AuthRequestDto;
import by.rublevskaya.dto.auth.AuthResponseDto;
import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto requestDto) {
        AuthResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register-doctor")
    public ResponseEntity<String> registerDoctor(@Valid @RequestBody DoctorDto dto) {
        userService.registerDoctor(dto);
        return ResponseEntity.ok("Doctor registered successfully!");
    }

}