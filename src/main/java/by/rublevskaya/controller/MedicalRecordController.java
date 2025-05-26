package by.rublevskaya.controller;

import by.rublevskaya.dto.medicalrecord.MedicalRecordDto;
import by.rublevskaya.dto.medicalrecord.MedicalRecordResponseDto;
import by.rublevskaya.service.MedicalRecordCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/medicalrecords")
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordCrudService medicalRecordCrudService;

    @PostMapping
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord(@Valid @RequestBody MedicalRecordDto dto) {
        log.info("Received request to create medical record with data: {}", dto);
        MedicalRecordResponseDto createdRecord = medicalRecordCrudService.createMedicalRecord(dto);
        log.info("Medical record created successfully: {}", createdRecord);
        return ResponseEntity.ok(createdRecord);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicalRecordResponseDto>> getMedicalRecordsByUserId(@PathVariable Long userId) {
        log.info("Fetching medical records for user ID: {}", userId);
        List<MedicalRecordResponseDto> medicalRecords = medicalRecordCrudService.getMedicalRecordsByUserId(userId);
        log.info("Retrieved {} medical records for user ID: {}", medicalRecords.size(), userId);
        return ResponseEntity.ok(medicalRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> getMedicalRecordById(@PathVariable Long id) {
        log.info("Fetching medical record with ID: {}", id);
        MedicalRecordResponseDto medicalRecord = medicalRecordCrudService.getMedicalRecordById(id);
        if (medicalRecord != null) {
            log.info("Medical record with ID: {} retrieved successfully: {}", id, medicalRecord);
        } else {
            log.warn("Medical record with ID: {} not found", id);
        }
        return ResponseEntity.ok(medicalRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> updateMedicalRecord(
            @PathVariable Long id, @Valid @RequestBody MedicalRecordDto dto) {
        log.info("Received update request for medical record with ID: {} using data: {}", id, dto);
        MedicalRecordResponseDto updatedRecord = medicalRecordCrudService.updateMedicalRecord(id, dto);
        log.info("Medical record with ID: {} updated successfully: {}", id, updatedRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable Long id) {
        log.info("Received request to delete medical record with ID: {}", id);
        String responseMessage = medicalRecordCrudService.deleteMedicalRecord(id);
        log.info("Medical record with ID: {} deleted successfully", id);
        return ResponseEntity.ok(responseMessage);
    }
}