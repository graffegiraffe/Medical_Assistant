package by.rublevskaya.mapper;

import by.rublevskaya.dto.medication.MedicationDto;
import by.rublevskaya.dto.medication.MedicationResponseDto;
import by.rublevskaya.model.Medication;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapper {

    public Medication toEntity(MedicationDto dto) {
        Medication medication = new Medication();
        medication.setUserId(dto.getUserId());
        medication.setName(dto.getName());
        medication.setDosage(dto.getDosage());
        medication.setStartDate(dto.getStartDate());
        medication.setEndDate(dto.getEndDate());
        medication.setActive(dto.getActive() != null ? dto.getActive() : true);
        return medication;
    }

    public MedicationResponseDto toResponseDto(Medication medication) {
        MedicationResponseDto responseDto = new MedicationResponseDto();
        responseDto.setUserId(medication.getUserId());
        responseDto.setName(medication.getName());
        responseDto.setDosage(medication.getDosage());
        responseDto.setStartDate(medication.getStartDate());
        responseDto.setEndDate(medication.getEndDate());
        responseDto.setActive(medication.getActive());
        return responseDto;
    }

    public void updateEntity(Medication medication, MedicationDto dto) {
        if (dto.getName() != null) medication.setName(dto.getName());
        if (dto.getDosage() != null) medication.setDosage(dto.getDosage());
        if (dto.getStartDate() != null) medication.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) medication.setEndDate(dto.getEndDate());
        if (dto.getActive() != null) medication.setActive(dto.getActive());
    }
}