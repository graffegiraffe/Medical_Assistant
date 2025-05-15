package by.rublevskaya.mapper;

import by.rublevskaya.dto.medicalrecord.MedicalRecordDto;
import by.rublevskaya.dto.medicalrecord.MedicalRecordResponseDto;
import by.rublevskaya.model.MedicalRecord;

import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    public MedicalRecord toEntity(MedicalRecordDto dto) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(dto.getUserId());
        medicalRecord.setTitle(dto.getTitle());
        medicalRecord.setDate(dto.getDate());
        medicalRecord.setDescription(dto.getDescription());
        medicalRecord.setDoctorNotes(dto.getDoctorNotes());
        medicalRecord.setType(MedicalRecord.RecordType.valueOf(dto.getType()));
        return medicalRecord;
    }

    public MedicalRecordResponseDto toResponseDto(MedicalRecord medicalRecord) {
        MedicalRecordResponseDto response = new MedicalRecordResponseDto();
        response.setId(medicalRecord.getId());
        response.setUserId(medicalRecord.getUserId());
        response.setTitle(medicalRecord.getTitle());
        response.setDate(medicalRecord.getDate());
        response.setDescription(medicalRecord.getDescription());
        response.setDoctorNotes(medicalRecord.getDoctorNotes());
        response.setType(medicalRecord.getType().name());
        return response;
    }

    public void updateEntity(MedicalRecord medicalRecord, MedicalRecordDto dto) {
        if (dto.getTitle() != null) {
            medicalRecord.setTitle(dto.getTitle());
        }
        if (dto.getDate() != null) {
            medicalRecord.setDate(dto.getDate());
        }
        if (dto.getDescription() != null) {
            medicalRecord.setDescription(dto.getDescription());
        }
        if (dto.getDoctorNotes() != null) {
            medicalRecord.setDoctorNotes(dto.getDoctorNotes());
        }
        if (dto.getType() != null) {
            medicalRecord.setType(MedicalRecord.RecordType.valueOf(dto.getType()));
        }
    }
}