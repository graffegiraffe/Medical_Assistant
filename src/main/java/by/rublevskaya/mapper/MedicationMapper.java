package by.rublevskaya.mapper;

import by.rublevskaya.dto.medication.MedicationDto;
import by.rublevskaya.dto.medication.MedicationResponseDto;
import by.rublevskaya.model.Medication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MedicationMapper {

    public Medication toEntity(MedicationDto dto) {
        log.info("Mapping MedicationDto to Medication entity: {}", dto);
        Medication medication = new Medication();
        medication.setUserId(dto.getUserId());
        medication.setName(dto.getName());
        medication.setDosage(dto.getDosage());
        medication.setStartDate(dto.getStartDate());
        medication.setEndDate(dto.getEndDate());
        medication.setActive(dto.getActive() != null ? dto.getActive() : true);
        log.info("Mapped Medication entity: {}", medication);
        return medication;
    }

    public MedicationResponseDto toResponseDto(Medication medication) {
        log.info("Mapping Medication entity to MedicationResponseDto: {}", medication);
        MedicationResponseDto responseDto = new MedicationResponseDto();
        responseDto.setUserId(medication.getUserId());
        responseDto.setName(medication.getName());
        responseDto.setDosage(medication.getDosage());
        responseDto.setStartDate(medication.getStartDate());
        responseDto.setEndDate(medication.getEndDate());
        responseDto.setActive(medication.getActive());
        log.info("Mapped MedicationResponseDto: {}", responseDto);
        return responseDto;
    }

    public void updateEntity(Medication medication, MedicationDto dto) {
        log.info("Updating Medication entity with MedicationDto. Entity: {}, Dto: {}", medication, dto);
        if (dto.getName() != null) {
            medication.setName(dto.getName());
            log.info("Updated name: {}", dto.getName());
        }
        if (dto.getDosage() != null) {
            medication.setDosage(dto.getDosage());
            log.info("Updated dosage: {}", dto.getDosage());
        }
        if (dto.getStartDate() != null) {
            medication.setStartDate(dto.getStartDate());
            log.info("Updated startDate: {}", dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            medication.setEndDate(dto.getEndDate());
            log.info("Updated endDate: {}", dto.getEndDate());
        }
        if (dto.getActive() != null) {
            medication.setActive(dto.getActive());
            log.info("Updated active status: {}", dto.getActive());
        }
        log.info("Updated Medication entity: {}", medication);
    }
}