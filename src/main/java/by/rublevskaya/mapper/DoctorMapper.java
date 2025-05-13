package by.rublevskaya.mapper;

import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.doctor.DoctorResponseDto;
import by.rublevskaya.dto.doctor.DoctorUpdateDto;
import by.rublevskaya.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorDto dto) {
        Doctor doctor = new Doctor();
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setClinicName(dto.getClinicName());
        doctor.setFullName(dto.getFullName());
        doctor.setUsername(dto.getUsername());
        doctor.setActive(dto.getIsActive());
        return doctor;
    }

    public DoctorDto toDto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();
        dto.setSpecialty(doctor.getSpecialty());
        dto.setLicenseNumber(doctor.getLicenseNumber());
        dto.setClinicName(doctor.getClinicName());
        dto.setFullName(doctor.getFullName());
        dto.setUsername(doctor.getUsername());
        dto.setIsActive(doctor.isActive());
        return dto;
    }

    public Doctor fromRegistrationDto(DoctorDto registrationDto) {
        Doctor doctor = new Doctor();
        doctor.setSpecialty(registrationDto.getSpecialty());
        doctor.setLicenseNumber(registrationDto.getLicenseNumber());
        doctor.setClinicName(registrationDto.getClinicName());
        doctor.setFullName(registrationDto.getFullName());
        doctor.setUsername(registrationDto.getUsername());
        doctor.setActive(registrationDto.getIsActive());
        return doctor;
    }

    public DoctorResponseDto toResponseDto(Doctor doctor) {
        DoctorResponseDto responseDto = new DoctorResponseDto();
        responseDto.setSpecialty(doctor.getSpecialty());
        responseDto.setLicenseNumber(doctor.getLicenseNumber());
        responseDto.setClinicName(doctor.getClinicName());
        responseDto.setFullName(doctor.getFullName());
        responseDto.setUsername(doctor.getUsername());
        responseDto.setIsActive(doctor.isActive());
        return responseDto;
    }

    public void updateEntity(Doctor doctor, DoctorUpdateDto dto) {
        if (dto.getSpecialty() != null) {
            doctor.setSpecialty(dto.getSpecialty());
        }
        if (dto.getLicenseNumber() != null) {
            doctor.setLicenseNumber(dto.getLicenseNumber());
        }
        if (dto.getClinicName() != null) {
            doctor.setClinicName(dto.getClinicName());
        }
        if (dto.getFullName() != null) {
            doctor.setFullName(dto.getFullName());
        }
        if (dto.getUsername() != null) {
            doctor.setUsername(dto.getUsername());
        }
        if (dto.getIsActive() != null) {
            doctor.setActive(dto.getIsActive());
        }
    }
}