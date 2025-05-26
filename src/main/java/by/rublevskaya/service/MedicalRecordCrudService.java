package by.rublevskaya.service;

import by.rublevskaya.dto.medicalrecord.MedicalRecordDto;
import by.rublevskaya.dto.medicalrecord.MedicalRecordResponseDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.MedicalRecordMapper;
import by.rublevskaya.model.MedicalRecord;
import by.rublevskaya.repository.DoctorRepository;
import by.rublevskaya.repository.MedicalRecordRepository;
import by.rublevskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordCrudService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordDto dto) {
        log.info("Creating a new medical record for User ID: {}, Doctor License: {}",
                dto.getUserId(), dto.getLicenseNumber());

        var user = userRepository.findById(dto.getUserId()).orElseThrow(() -> {
            log.warn("User not found with ID: {}", dto.getUserId());
            return new CustomException("User not found with ID " + dto.getUserId());
        });

        var doctor = doctorRepository.findByLicenseNumber(dto.getLicenseNumber()).orElseThrow(() -> {
            log.warn("Doctor not found with license number: {}", dto.getLicenseNumber());
            return new CustomException("Doctor not found with license number " + dto.getLicenseNumber());
        });

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(dto);
        medicalRecord.setUserId(user.getId());
        medicalRecord.setDoctorId(doctor.getId());
        log.debug("Mapped MedicalRecordDto to MedicalRecord entity: {}", medicalRecord);

        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        log.info("Medical record successfully created with ID: {}", savedRecord.getId());
        return medicalRecordMapper.toResponseDto(savedRecord);
    }


    @Transactional(readOnly = true)
    public List<MedicalRecordResponseDto> getMedicalRecordsByUserId(Long userId) {
        log.info("Fetching medical records for User ID: {}", userId);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByUserId(userId);

        if (medicalRecords.isEmpty()) {
            log.warn("No medical records found for User ID: {}", userId);
            throw new CustomException("No medical records found for user with ID " + userId);
        }

        log.info("Successfully fetched {} medical records for User ID: {}", medicalRecords.size(), userId);
        return medicalRecordRepository.findByUserId(userId)
                .stream()
                .map(medicalRecordMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponseDto getMedicalRecordById(Long id) {
        log.info("Fetching medical record with ID: {}", id);
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> {
            log.warn("Medical record not found with ID: {}", id);
            return new CustomException("Medical record not found with ID " + id);
        });

        log.info("Successfully fetched medical record with ID: {}", id);
        return medicalRecordMapper.toResponseDto(medicalRecord);
    }

    @Transactional
    public MedicalRecordResponseDto updateMedicalRecord(Long id, MedicalRecordDto dto) {
        log.info("Updating medical record with ID: {}", id);
        MedicalRecord existingRecord = medicalRecordRepository.findById(id).orElseThrow(() -> {
            log.warn("Medical record not found with ID: {}", id);
            return new CustomException("Medical record not found with ID " + id);
        });

        log.debug("Updating medical record properties for ID: {}. New Data: {}", id, dto);
        medicalRecordMapper.updateEntity(existingRecord, dto);

        MedicalRecord updatedRecord = medicalRecordRepository.save(existingRecord);
        log.info("Successfully updated medical record with ID: {}", updatedRecord.getId());
        return medicalRecordMapper.toResponseDto(updatedRecord);
    }

    @Transactional
    public String deleteMedicalRecord(Long id) {
        log.info("Attempting to delete medical record with ID: {}", id);
        if (!medicalRecordRepository.existsById(id)) {
            log.warn("Medical record with ID {} does not exist. Deletion aborted.", id);
            throw new CustomException("Medical record not found with ID " + id);
        }

        medicalRecordRepository.deleteById(id);
        log.info("Medical record with ID {} has been successfully deleted.", id);
        return "Medical record with ID " + id + " has been successfully deleted.";
    }
}