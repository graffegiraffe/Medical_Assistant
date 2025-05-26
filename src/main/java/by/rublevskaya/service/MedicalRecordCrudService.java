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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordCrudService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordDto dto) {
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found with ID " + dto.getUserId()));

        var doctor = doctorRepository.findByLicenseNumber(dto.getLicenseNumber())
                .orElseThrow(() -> new CustomException(
                        "Doctor not found with license number " + dto.getLicenseNumber()));

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(dto);
        medicalRecord.setUserId(user.getId());
        medicalRecord.setDoctorId(doctor.getId());

        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toResponseDto(savedRecord);
    }


    @Transactional(readOnly = true)
    public List<MedicalRecordResponseDto> getMedicalRecordsByUserId(Long userId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByUserId(userId);
        if (medicalRecords.isEmpty()) {
            throw new CustomException("No medical records found for user with ID " + userId);
        }
        return medicalRecordRepository.findByUserId(userId)
                .stream()
                .map(medicalRecordMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponseDto getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException("Medical record not found with ID " + id));
        return medicalRecordMapper.toResponseDto(medicalRecord);
    }

    @Transactional
    public MedicalRecordResponseDto updateMedicalRecord(Long id, MedicalRecordDto dto) {
        MedicalRecord existingRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException("Medical record not found with ID " + id));

        medicalRecordMapper.updateEntity(existingRecord, dto);
        MedicalRecord updatedRecord = medicalRecordRepository.save(existingRecord);
        return medicalRecordMapper.toResponseDto(updatedRecord);
    }

    @Transactional
    public String deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new CustomException("Medical record not found with ID " + id);
        }
        medicalRecordRepository.deleteById(id);
        return "Medical record with ID " + id + " has been successfully deleted.";
    }
}