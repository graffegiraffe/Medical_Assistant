package by.rublevskaya.mapper;

import by.rublevskaya.dto.clinic.ClinicDto;
import by.rublevskaya.dto.clinic.ClinicResponseDto;
import by.rublevskaya.model.Clinic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClinicMapper {

    public ClinicResponseDto toDto(Clinic clinic) {
        log.info("Mapping Clinic entity to ClinicResponseDto: {}", clinic);
        ClinicResponseDto dto = new ClinicResponseDto();
        dto.setName(clinic.getName());
        dto.setAddress(clinic.getAddress());
        dto.setPhone(clinic.getPhone());
        log.info("Mapped ClinicResponseDto: {}", dto);
        return dto;
    }

    public List<ClinicResponseDto> toDtoList(List<Clinic> clinics) {
        log.info("Mapping list of Clinic entities to list of ClinicResponseDto. Total clinics: {}", clinics.size());
        List<ClinicResponseDto> dtoList = clinics.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Mapped list of ClinicResponseDto: {}", dtoList);
        return dtoList;
    }

    public Clinic toEntity(ClinicDto dto) {
        log.info("Mapping ClinicDto to Clinic entity: {}", dto);
        Clinic clinic = new Clinic();
        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        clinic.setPhone(dto.getPhone());
        log.info("Mapped Clinic entity: {}", clinic);
        return clinic;
    }

    public ClinicResponseDto toResponseDto(Clinic clinic) {
        log.info("Mapping Clinic entity to ClinicResponseDto: {}", clinic);
        ClinicResponseDto responseDto = new ClinicResponseDto();
        responseDto.setName(clinic.getName());
        responseDto.setAddress(clinic.getAddress());
        responseDto.setPhone(clinic.getPhone());
        log.info("Mapped ClinicResponseDto: {}", responseDto);
        return responseDto;
    }
}