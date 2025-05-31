package by.rublevskaya.service;

import by.rublevskaya.aop.Timed;
import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.doctor.DoctorResponseDto;
import by.rublevskaya.dto.doctor.DoctorUpdateDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.DoctorMapper;
import by.rublevskaya.model.Doctor;
import by.rublevskaya.model.Security;
import by.rublevskaya.model.User;
import by.rublevskaya.repository.ClinicRepository;
import by.rublevskaya.repository.DoctorRepository;
import by.rublevskaya.repository.SecurityRepository;
import by.rublevskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorCrudService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    @Timed
    public DoctorResponseDto createDoctor(DoctorDto registrationDto) {
        if (doctorRepository.existsByLicenseNumber(registrationDto.getLicenseNumber())) {
            throw new CustomException("A doctor with this license number already exists." + HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new CustomException(String.format("Email '%s' is already in use. Please choose another one.",
                    registrationDto.getEmail()));
        }
        if (doctorRepository.existsByUsername(registrationDto.getUsername())) {
            throw new CustomException("A doctor with username '" + registrationDto.getUsername() + "' already exists." + HttpStatus.BAD_REQUEST);
        }
        if (!clinicRepository.existsByName(registrationDto.getClinicName())) {
            throw new CustomException("Clinic with name " + registrationDto.getClinicName() + " does not exist." + HttpStatus.BAD_REQUEST);
        }
            Doctor doctor = doctorMapper.toEntity(registrationDto);
            Doctor savedDoctor = doctorRepository.save(doctor);
            log.info("New doctor created successfully. Doctor ID: {}", savedDoctor.getId());

            User user = new User();
            user.setUsername(registrationDto.getUsername());
            user.setEmail(registrationDto.getEmail());
            user.setBirthDate(registrationDto.getBirthDate());
            user.setBloodType(registrationDto.getBloodType());
            user.setDoctorId(savedDoctor.getId());

            userRepository.save(user);

            Security security = new Security();
            security.setLogin(registrationDto.getUsername());
            security.setPassword(registrationDto.getPassword());
            security.setRole("DOCTOR");
            security.setUserId(user.getId());
            securityRepository.save(security);
            return doctorMapper.toResponseDto(savedDoctor);
    }

    @Timed
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> getAllDoctors() {
        log.info("Fetching all doctors.");
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            log.warn("No doctors found.");
            throw new CustomException("No doctors found.");
        }
        log.info("Successfully fetched {} doctors.", doctors.size());
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DoctorResponseDto getDoctorById(Long id) {
        log.info("Fetching doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> {
            log.warn("Doctor with ID {} not found.", id);
            return new CustomException("Doctor with ID " + id + " not found");
        });
        log.info("Successfully fetched doctor with ID: {}", id);
        return doctorMapper.toResponseDto(doctor);
    }

    @Transactional
    public DoctorDto updateDoctor(Long id, DoctorDto dto) {
        log.info("Updating doctor with ID: {}", id);
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Doctor with ID " + id + " not found."));

        Doctor updatedDoctor = doctorMapper.toEntity(dto);
        updatedDoctor.setId(existingDoctor.getId());
        Doctor savedDoctor = doctorRepository.save(updatedDoctor);
        log.info("Successfully updated doctor with ID: {}", updatedDoctor.getId());
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public DoctorDto partialUpdateDoctor(Long id, DoctorUpdateDto dto) {
        log.info("Partially updating doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> {
            log.warn("Doctor with ID {} not found.", id);
            return new CustomException("Doctor with ID " + id + " not found");
        });

        doctorMapper.updateEntity(doctor, dto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor with partially updated successfully." + id);
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        log.info("Attempting to delete doctor with ID: {}", id);
        if (!doctorRepository.existsById(id)) {
            log.warn("Doctor with ID {} does not exist. Deletion aborted.", id);
            throw new CustomException("Doctor with ID " + id + " does not exist");
        }
        doctorRepository.deleteById(id);
        log.info("Doctor with ID {} has been successfully deleted.", id);
    }
}