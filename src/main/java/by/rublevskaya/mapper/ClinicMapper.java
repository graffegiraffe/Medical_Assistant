package by.rublevskaya.mapper;

import by.rublevskaya.dto.clinic.ClinicDto;
import by.rublevskaya.dto.clinic.ClinicResponseDto;
import by.rublevskaya.model.Clinic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClinicMapper {

    public ClinicResponseDto toDto(Clinic clinic) {
        ClinicResponseDto dto = new ClinicResponseDto();
        dto.setName(clinic.getName());
        dto.setAddress(clinic.getAddress());
        dto.setPhone(clinic.getPhone());
        return dto;
    }

    public List<ClinicResponseDto> toDtoList(List<Clinic> clinics) {
        return clinics.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Clinic toEntity(ClinicDto dto) {
        Clinic clinic = new Clinic();
        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        clinic.setPhone(dto.getPhone());
        return clinic;
    }

    public ClinicResponseDto toResponseDto(Clinic clinic) {
        ClinicResponseDto responseDto = new ClinicResponseDto();
        responseDto.setName(clinic.getName());
        responseDto.setAddress(clinic.getAddress());
        responseDto.setPhone(clinic.getPhone());
        return responseDto;
    }
}