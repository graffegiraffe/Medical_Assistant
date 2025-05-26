package by.rublevskaya.service;

import by.rublevskaya.dto.auth.AuthRequestDto;
import by.rublevskaya.dto.auth.AuthResponseDto;
import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.DoctorMapper;
import by.rublevskaya.mapper.UserMapper;
import by.rublevskaya.model.Doctor;
import by.rublevskaya.model.Security;
import by.rublevskaya.model.User;
import by.rublevskaya.repository.ClinicRepository;
import by.rublevskaya.repository.DoctorRepository;
import by.rublevskaya.repository.SecurityRepository;
import by.rublevskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final UserMapper userMapper;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final ClinicRepository clinicRepository;

    @Transactional
    public void registerUser(UserDto dto) {
        log.info("Registering new user. Email: {}, Username: {}", dto.getEmail(), dto.getUsername());
        if (userRepository.findByEmail(dto.getEmail()).isPresent() || userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new CustomException("A user with this email or username already exists.");
        }
        User user = userMapper.toEntity(dto);
        user.setDoctorId(null);
        User savedUser = userRepository.save(user);
        log.info("User successfully registered. User ID: {}", user.getId());

        Security security = new Security();
        security.setLogin(dto.getUsername());
        security.setPassword(dto.getPassword());
        security.setRole("USER");
        security.setUserId(savedUser.getId());
        security.setCreated(LocalDateTime.now());
        security.setUpdated(LocalDateTime.now());
        securityRepository.save(security);
    }

    @Transactional
    public void registerDoctor(DoctorDto dto) {
        log.info("Registering new doctor. Email: {}, LicenseNumber: {}", dto.getEmail(), dto.getLicenseNumber());
        if (!clinicRepository.existsByName(dto.getClinicName())) {
            throw new CustomException("Clinic with name " + dto.getClinicName() + " does not exist.");
        }
        if (doctorRepository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new CustomException("A doctor with this license number already exists.");
        }
        if (securityRepository.existsByLogin(dto.getUsername())) {
            throw new CustomException("A user with this username already exists.");
        }
        User user = new User();
        user.setUsername(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setBloodType(dto.getBloodType());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        Doctor doctor = doctorMapper.fromRegistrationDto(dto);
        doctor.setUsername(dto.getUsername());
        doctorRepository.save(doctor);
        log.info("Doctor successfully registered. Doctor ID: {}", doctor.getId());

        Security security = new Security();
        security.setLogin(dto.getUsername());
        security.setPassword(dto.getPassword());
        security.setRole("DOCTOR");
        security.setUserId(savedUser.getId());
        security.setCreated(LocalDateTime.now());
        security.setUpdated(LocalDateTime.now());
        securityRepository.save(security);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto loginUser(AuthRequestDto authRequest) {
        log.info("Attempting to log in user");
        Security security = securityRepository.findByLogin(authRequest.getLogin())
                .orElseThrow(() -> new CustomException("Invalid login or password"));
        if (!security.getPassword().equals(authRequest.getPassword())) {
            throw new CustomException("Invalid login or password");
        }
        AuthResponseDto response = new AuthResponseDto();
        response.setLogin(security.getLogin());
        response.setRole(security.getRole());
        log.info("User successfully logged in.");
        return response;
    }
}