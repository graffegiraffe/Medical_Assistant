package by.rublevskaya.mapper;

import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.doctor.DoctorResponseDto;
import by.rublevskaya.dto.doctor.DoctorUpdateDto;
import by.rublevskaya.model.Doctor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DoctorMapper {

    public Doctor toEntity(DoctorDto dto) {
        log.info("Mapping DoctorDto to Doctor entity: {}", dto);
        Doctor doctor = new Doctor();
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setClinicName(dto.getClinicName());
        doctor.setFullName(dto.getFullName());
        doctor.setUsername(dto.getUsername());
        doctor.setActive(dto.getIsActive());
        log.info("Mapped Doctor entity: {}", doctor);
        return doctor;
    }

    public DoctorDto toDto(Doctor doctor) {
        log.info("Mapping Doctor entity to DoctorDto: {}", doctor);
        DoctorDto dto = new DoctorDto();
        dto.setSpecialty(doctor.getSpecialty());
        dto.setLicenseNumber(doctor.getLicenseNumber());
        dto.setClinicName(doctor.getClinicName());
        dto.setFullName(doctor.getFullName());
        dto.setUsername(doctor.getUsername());
        dto.setIsActive(doctor.isActive());
        log.info("Mapped DoctorDto: {}", dto);
        return dto;
    }

    public Doctor fromRegistrationDto(DoctorDto registrationDto) {
        log.info("Mapping registration DoctorDto to Doctor entity: {}", registrationDto);
        Doctor doctor = new Doctor();
        doctor.setSpecialty(registrationDto.getSpecialty());
        doctor.setLicenseNumber(registrationDto.getLicenseNumber());
        doctor.setClinicName(registrationDto.getClinicName());
        doctor.setFullName(registrationDto.getFullName());
        doctor.setUsername(registrationDto.getUsername());
        doctor.setActive(registrationDto.getIsActive());
        log.info("Mapped Doctor entity from registration DTO: {}", doctor);
        return doctor;
    }

    public DoctorResponseDto toResponseDto(Doctor doctor) {
        log.info("Mapping Doctor entity to DoctorResponseDto: {}", doctor);
        DoctorResponseDto responseDto = new DoctorResponseDto();
        responseDto.setSpecialty(doctor.getSpecialty());
        responseDto.setLicenseNumber(doctor.getLicenseNumber());
        responseDto.setClinicName(doctor.getClinicName());
        responseDto.setFullName(doctor.getFullName());
        responseDto.setUsername(doctor.getUsername());
        responseDto.setIsActive(doctor.isActive());
        log.info("Mapped DoctorResponseDto: {}", responseDto);
        return responseDto;
    }

    public void updateEntity(Doctor doctor, DoctorUpdateDto dto) {
        log.info("Updating Doctor entity with DoctorUpdateDto. Entity: {}, UpdateDto: {}", doctor, dto);
        if (dto.getSpecialty() != null) {
            doctor.setSpecialty(dto.getSpecialty());
            log.info("Updated specialty: {}", dto.getSpecialty());
        }
        if (dto.getLicenseNumber() != null) {
            doctor.setLicenseNumber(dto.getLicenseNumber());
            log.info("Updated license number: {}", dto.getLicenseNumber());
        }
        if (dto.getClinicName() != null) {
            doctor.setClinicName(dto.getClinicName());
            log.info("Updated clinic name: {}", dto.getClinicName());
        }
        if (dto.getFullName() != null) {
            doctor.setFullName(dto.getFullName());
            log.info("Updated full name: {}", dto.getFullName());
        }
        if (dto.getUsername() != null) {
            doctor.setUsername(dto.getUsername());
            log.info("Updated username: {}", dto.getUsername());
        }
        if (dto.getIsActive() != null) {
            doctor.setActive(dto.getIsActive());
            log.info("Updated active status: {}", dto.getIsActive());
        }
        log.info("Updated Doctor entity: {}", doctor);
    }
}
