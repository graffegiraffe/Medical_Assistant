package by.rublevskaya.mapper;

import by.rublevskaya.dto.medicalrecord.MedicalRecordDto;
import by.rublevskaya.dto.medicalrecord.MedicalRecordResponseDto;
import by.rublevskaya.model.MedicalRecord;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MedicalRecordMapper {

    public MedicalRecord toEntity(MedicalRecordDto dto) {
        log.info("Mapping MedicalRecordDto to MedicalRecord entity: {}", dto);
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(dto.getUserId());
        medicalRecord.setTitle(dto.getTitle());
        medicalRecord.setDate(dto.getDate());
        medicalRecord.setDescription(dto.getDescription());
        medicalRecord.setDoctorNotes(dto.getDoctorNotes());
        medicalRecord.setType(MedicalRecord.RecordType.valueOf(dto.getType()));
        log.info("Mapped MedicalRecord entity: {}", medicalRecord);
        return medicalRecord;
    }

    public MedicalRecordResponseDto toResponseDto(MedicalRecord medicalRecord) {
        log.info("Mapping MedicalRecord entity to MedicalRecordResponseDto: {}", medicalRecord);
        MedicalRecordResponseDto response = new MedicalRecordResponseDto();
        response.setTitle(medicalRecord.getTitle());
        response.setDate(medicalRecord.getDate());
        response.setDescription(medicalRecord.getDescription());
        response.setDoctorNotes(medicalRecord.getDoctorNotes());
        response.setType(medicalRecord.getType().name());
        log.info("Mapped MedicalRecordResponseDto: {}", response);
        return response;
    }

    public void updateEntity(MedicalRecord medicalRecord, MedicalRecordDto dto) {
        log.info("Updating MedicalRecord entity with MedicalRecordDto. Entity: {}, Dto: {}", medicalRecord, dto);
        if (dto.getTitle() != null) {
            medicalRecord.setTitle(dto.getTitle());
            log.info("Updated title: {}", dto.getTitle());
        }
        if (dto.getDate() != null) {
            medicalRecord.setDate(dto.getDate());
            log.info("Updated date: {}", dto.getDate());
        }
        if (dto.getDescription() != null) {
            medicalRecord.setDescription(dto.getDescription());
            log.info("Updated description: {}", dto.getDescription());
        }
        if (dto.getDoctorNotes() != null) {
            medicalRecord.setDoctorNotes(dto.getDoctorNotes());
            log.info("Updated doctorNotes: {}", dto.getDoctorNotes());
        }
        if (dto.getType() != null) {
            medicalRecord.setType(MedicalRecord.RecordType.valueOf(dto.getType()));
            log.info("Updated type: {}", dto.getType());
        }
        log.info("Updated MedicalRecord entity: {}", medicalRecord);
    }
}