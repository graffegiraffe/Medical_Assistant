package by.rublevskaya.service;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorCrudService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
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
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DoctorResponseDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Doctor with ID " + id + " not found."));
        return doctorMapper.toResponseDto(doctor);
    }

    @Transactional
    public DoctorDto updateDoctor(Long id, DoctorDto dto) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Doctor with ID " + id + " not found."));

        Doctor updatedDoctor = doctorMapper.toEntity(dto);
        updatedDoctor.setId(existingDoctor.getId());
        Doctor savedDoctor = doctorRepository.save(updatedDoctor);
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public DoctorDto partialUpdateDoctor(Long id, DoctorUpdateDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Doctor with ID " + id + " not found."));

        doctorMapper.updateEntity(doctor, dto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new CustomException("Doctor with ID " + id + " not found.");
        }
        doctorRepository.deleteById(id);
    }

}